# Reactor Sequence에 대한 디버깅 방법
1. Hooks.onOperatorDebug()
   - 모든 Operator 에 대해 스택 트레이스 -> 권장 X
   - IDE 자동 설정법: File > Settings > Language & Framework > Reactive Streams > Hook.onOperatorDebug() 선택
2. 운영환경에서 Webflux인 경우, compile 'io.projectreactor.reactor-tools' 를 통해 스택트레이스 캡쳐 비용을 지불하지 않고 디버깅 가능
   - Spring Boot 는 spring.reactor.debug-agent.enabled 프로퍼티의 값을 true 로 자동 구성
3. checkpoint() Operator
   - 특정 Operator() 체인 내의 스택트레이스만 캡처
4. log() Operator
   - 개수 제한 X

* 참고
- Assembly: Operator() 에서 새롭게 반환하는 Flux/Mono가 선언된 지점
- Traceback: 에러가 발생한 Operator의 스택트레이스를 캡처한 Assembly 정보를 말함.