
### 기초 네트워크 개념

OSI 7계층 OSI 7계층 각각의 역할을 설명해보세요.
TCP/IP 4계층 모델과의 차이는 무엇인가요?
IP / MAC IP 주소와 MAC 주소의 차이는 무엇인가요?
DNS 브라우저에 <www.google.com을> 입력했을 때 일어나는 과정을 설명해보세요.
HTTP / HTTPS HTTP와 HTTPS의 차이점은?
SSL/TLS의 역할은 무엇인가요?
포트 포트 번호의 역할은 무엇이고, 80/443 포트는 각각 무엇을 의미하나요?
NAT NAT(Network Address Translation)의 개념과 필요한 이유를 설명해보세요.

### TCP / UDP 심화

TCP vs UDP 차이점과 각각 어떤 상황에서 사용하는지 예를 들어 설명해보세요.
3-way handshake TCP 연결 과정(3-way handshake)을 설명하고, 왜 3번의 과정이 필요한지 말해보세요.
4-way handshake TCP 연결 종료 과정은 어떻게 되나요?
흐름 제어 / 혼잡 제어 TCP에서 패킷 손실이 발생했을 때 어떤 일이 일어나나요?
흐름 제어와 혼잡 제어의 차이는?
Keep-Alive HTTP Keep-Alive는 어떤 역할을 하나요?

### HTTP 실무 관점

HTTP Method GET, POST, PUT, PATCH, DELETE의 차이를 설명해주세요.
상태 코드 200, 201, 204, 400, 401, 403, 404, 500의 의미를 각각 설명해주세요.
쿠키/세션 쿠키와 세션의 차이점은?
캐싱 HTTP 캐싱 헤더에는 어떤 것들이 있고, 어떤 상황에서 사용하나요?
REST API RESTful API란 무엇인가요? REST 원칙 중 하나를 설명해보세요.
웹소켓 WebSocket은 HTTP와 어떤 차이가 있나요? 언제 사용하는 게 좋을까요?
HTTP 버전별 차이

### 네트워크 + 서버 아키텍처 관점

로드밸런싱 L4 vs L7 로드밸런서의 차이는 무엇인가요?
Sticky session은 언제 필요할까요?
리버스 프록시 Nginx가 리버스 프록시로 동작한다는 건 무슨 뜻인가요?
CDN CDN은 어떻게 성능을 개선하나요?
CORS CORS 에러는 왜 발생하고, 해결 방법은 무엇인가요?
Connection Pool DB Connection Pool을 사용하는 이유는 무엇인가요?
타임아웃 API 타임아웃은 왜 필요한가요? 클라이언트와 서버 중 어디서 설정하나요?
장애 대응 네트워크 지연이 발생했을 때 어떻게 문제를 진단하나요? (ping, traceroute, curl 등 활용)

### 보안 관련 (HTTP, 네트워크, 인증)

HTTPS TLS Handshake 과정을 설명해주세요.
인증 Basic Auth, Token Auth, OAuth의 차이를 설명해보세요.
공격 CSRF와 XSS 공격의 차이는 무엇인가요?
방화벽 방화벽은 어떤 계층에서 동작하나요?

### 추가 질문

“브라우저에서 요청이 서버까지 도달하기까지의 전체 과정을 설명해주세요.”
(DNS → TCP 연결 → SSL → HTTP 요청 → 응답 → 렌더링 순서로 말하면 👍)

“서버 응답이 느릴 때 어디를 먼저 의심하나요?”
(네트워크, DNS, 서버 로직, DB 쿼리, 외부 API 등 단계별 접근)

“API 요청이 타임아웃이 났을 때 원인을 찾는 과정을 설명해보세요.”

“로드밸런서와 WAS 사이의 통신은 어떤 방식으로 이루어지나요?”
