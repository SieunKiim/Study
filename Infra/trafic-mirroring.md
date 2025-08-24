# 트래픽 미러링이란?

* 실제 사용자의 요청을 그대로 복사해서 별도의 환경으로 전달하는 기술
* 원본 트래픽은 기존 시스템에서 정상 처리되고, 복사본은 테스트 환경으로 보내짐

## 주로 언제 사용할까?

* 실제 데이터로 테스트: 가상의 테스트 데이터가 아닌 실제 운영 트래픽 패턴으로 검증
* 무중단 검증: 사용자에게 영향 없이 새 버전이나 시스템 변경사항 테스트 (_AB테스트 등_)
* 성능 검증: 실제 부하 상황에서의 성능과 안정성 확인 (_캐싱 적용률 확인 등_)
* 호환성 검증: 기존 시스템과 새 시스템의 응답 비교 (_API 버전업 및 데이터 마이그레이션 등_)

## 주의 사항

* 중복된 처리로 인한 상태변경 및 알림 발송 등의 중복 동작수행에 주의해야 한다.
-> Read Only를 바탕으로한 동작
* 수행 동작 시간차로 인한 Read결과값이 다를 수 있음.
-> 스냅샷 기반 테스트 진행 및 동기화 시간 고려한 테스트
* 동일한 요청의 중복으로인한 인프라 비용 배로 증가
-> 피크 시간 테스트 조정

### 추가 궁금증

#### 스냅샷 기반 테스트가 뭐지?

위에서 설명 된 대로, 네트워크 혹은 작업 소요시간에 따라 동일 조건 조회지만 다른 결과가 반환될 수 있다. 이런 문제점을 해결하기 위해 조회 조건에 대한 스냅샷을 저장하고, 미러링된 서비스에서 해당 스냅샷을 통해 동일한 결과를 얻어낼 수 있는 구조 설계를 뜻한다.
방법은 Redis 저장 방식, Kafka 활용 방식 Replica DB 방식 등 여러 방법이 소개되어있지만, 개인적으로는 Redis 방식이 괜찮아보인다.

`Production` 서비스에서는 서비스로직에 필요한 조회된 내용을 Redis에 올려두고, `Mirroring` 서비스에서는 동일한 Request를 기준으로 Redis에서 조회하여 로직을 수행한다. (아래 예시 참조)

Redis 방식이 괜찮다고 생각한 이유는, DB 조회하는 부분을 Interface화를 통해서 미러링 테스트 시에는 Redis 조회로 사용하고, Production 환경에서는 DB 조회로 각각의 구현체 구성을 통해서 전략적으로 구성 가능할 것이라고 생각했다.

물론 서비스로직을 수행하기 전 모든 조회가 마무리되어야하고 중간중간 업데이트된 값을 활용하는 부분에 대해서는 구체적인 대응 방안이 필요하다고 생각하지만, Application단에서 설계할 수 있는 부분에서는 최선의 방법이라고 생각된다.

``` java
@Service
@Profile("production")
public class ProductionOrderService {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Transactional
    public OrderResult processOrder(OrderRequest request) {
        // 1. 고유 Request ID 생성
        String requestId = generateRequestId(request);
        
        // 2. 스냅샷 생성 및 Redis 저장
        OrderSnapshot snapshot = createSnapshot(request);
        redisTemplate.opsForValue().set(
            "snapshot:" + requestId, 
            snapshot, 
            Duration.ofMinutes(5) // TTL 5분
        );
        
        // 3. 실제 주문 처리
        OrderResult result = executeRealOrder(request);
        result.setRequestId(requestId);
        
        return result;
    }
    
    private String generateRequestId(OrderRequest request) {
        // 요청 내용으로 결정적 ID 생성 (원본과 미러가 동일한 ID)
        return DigestUtils.md5Hex(
            request.getUserId() + ":" + 
            request.getProductId() + ":" + 
            System.currentTimeMillis() / 1000 // 초 단위로 반올림
        );
    }
}

@Service
@Profile("mirror")
public class MirrorOrderService {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Retryable(value = {Exception.class}, maxAttempts = 3, delay = 100)
    @Transactional(readOnly = true)
    public OrderResult processOrder(OrderRequest request) {
        // 1. 동일한 방식으로 Request ID 생성
        String requestId = generateRequestId(request);
        
        // 2. Redis에서 스냅샷 조회 (재시도 로직)
        OrderSnapshot snapshot = waitForSnapshot(requestId);
        
        if (snapshot == null) {
            log.warn("스냅샷을 찾을 수 없음: {}", requestId);
            return createFallbackResult(request, requestId);
        }
        
        // 3. 스냅샷 데이터로 주문 처리 시뮬레이션
        return executeWithSnapshot(request, snapshot);
    }
    
    private OrderSnapshot waitForSnapshot(String requestId) {
        // 최대 2초간 대기하며 스냅샷 조회
        for (int i = 0; i < 20; i++) {
            OrderSnapshot snapshot = (OrderSnapshot) redisTemplate
                .opsForValue().get("snapshot:" + requestId);
            
            if (snapshot != null) {
                return snapshot;
            }
            
            try {
                Thread.sleep(100); // 100ms 대기
            } catch (InterruptedException e) {
                break;
            }
        }
        return null;
    }
}
```
