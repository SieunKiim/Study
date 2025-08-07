Spring Framework의 선언적 트랜잭션 구현 이해

우리는 Spring boot 프로젝트에서 Transactional한 로직이 필요할 때 `@Transactional` 이라는 어노테이션을 적용하여 편하게 기능을 활용한다. (`@EnableTransactionalManagement` 도 당연히 필요하다)

Spring Framework에서 위에 말한대로 선언적 트랜잭션은 AOP 프록시를 통해 TransactionInterceptor가 활성화되고, 트랜잭션 어드바이스가 메타데이터에 의해 구동된다. (!? 벌써 어려운데)

AOP와 트랜잭션 메타데이터를 결합하면 AOP를 사용하여 TransactionManager 메서트 호출을 중심으로 트랜잭션을 구동하는 AOP 프록시가 생성된다.

Spring Framework는 TransactionInterdeptor의 명령형 및 반응형 프로그래밍 모델에 대한 트랜잭션 관리를 제공합니다. 인터셉터는 메서드 반환 유형을 검사해서 원하는 트랜잭션 관리 방식을 감지한다.

* 명령형 트랜잭션 : PlatformTransactionManager
* 반응형 트랜잭션 : ReactiveTransctionManager
각각 유형별 구현이 필요하다

여기서 잠시… 포인트컷은 뭐고… 어드바이스는 뭐고…!?
위 용어들은 AOP에서 나오는 개념이다. (Aspect Oriented Programming)

* 어드바이스(Advice): 특정 조인 포인트에서 수행할 실제 로직(예: 트랜잭션 시작, 커밋, 롤백).
* 조인 포인트(Join Point): 어드바이스를 적용할 수 있는 프로그램 실행 시점(메서드 호출, 객체 생성 등). 스프링 AOP에서는 주로 메서드 실행 시점을 조인 포인트로 사용합니다.
* 포인트컷(Pointcut): 조인 포인트 중에서 어드바이스를 적용할 실제 지점을 선별하는 표현식.

위에 설정된 구성에 의해서 트랜잭션 프록시가 생성된다.
프록시는 트랜잭션 어드바이스를 사용하여 구성되게 된다.
프록시에서 적절한 메서드가 호출될 때 해당 메서드와 연관된 트랜잭션 구성에 따라 트랜잭션 시작, 일시 주안, 읽기전용 등으로 표시되는 작업이 수행된다.

트랜잭션 동작 중 롤백 요청에대한 가장 확실한 방법은 Exception을 Throw 하는 것이다.
Spring Framework의 트랜잭션 인프라 코드는 Exception 호출 스택에서 처리되지 않은 모든 작업을 버블링하여 롤백 여부를 판단한다.
디폴트 설정으로 unchecked exception (RuntimeException 하위)의 경우에만 트랜잭션 롤백이 진행된다.

프록시모드에서는 프록시를 통해 들어오는 외부 메서드 호출만 가로채기 된다. 즉 자체 호출은 호출된 메서드에 Transactional 표시가 있더라도 실제 트랜잭션으로 이어지지 않는다. (예시로 실제 구현내용 확인 도전해보기)

어노테이션 @Transactional 설정은 인터페이스 클래스 혹은 메서드가 트랜잭션 의미 체계를 가져야함을 지정하는 메타데이터다. (관련된 설정을 표기할 수 있음)
￼

Spring 에서 관리하는 트랜잭션은 물리,논리 두가지 유형의 트랜잭션으로 나뉜다.

* PROPAGATION_REQUIRED는 물리적 트랜잭션을 적용한다.
