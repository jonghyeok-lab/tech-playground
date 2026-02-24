# Reactor 의 Testing
- StepVerifier
  - Reactor Sequence에서 발생하는 Signal 이벤트 테스트
  - withVirtualTime() 과 VirtualTimeScheduler() 이용해서 시간 기반 테스트 진행
  - thenConsumeWhile() 을 이용해서 Backpressure 테스트 진행
  - expectAccessibleContext() 이용해서 Sequence 에 전파되는 Context를 테스트
  - recordWith(), consumeRecordedWith(), expectRecordedMatches() 등을 이용해 Record기반 테스트

## Signal 이벤트 기반 테스트
### expect()
- expectSubscription(): 구독이 이루어짐을 기대
- expectNext(T t): onNext Signal을 통해 전달되는 값이 파라미터로 전달된 값과 같음을 기대: 
- expectComplete() / expectError() : 해당 Signal이 전송되기를 기대
- expectNextCount(long count): 구독 시점 또는 이전(previous) expectNext()를 통해 기댓값이 평가된 데이터 이후부터 emit 된 수를 기대
- expectNoEvent(Duration duration): 특정 시간동안 Signal 이벤트가 발생하지 않음
- expectAccessibleContext(): 구독 시점 이후에 Context가 전파
- expectNextSequence(Iterable): emit된 데이터들이 파라미터로 전달된 Iterable의 요소와 매치됨을 기대.

### verify()
- 테스트 대상 Operator 체인에 대한 구독이 이루어지고 기댓값을 평가

## 시간 기반(Time-based) 테스트
- 가상의 시간을 이용해 미래에 실행되는 Reactor Sequence의 시간을 앞당겨 테스트할 수 있습니다.
  - withVirtualTime() 으로 가능
  - expectNoEvent(Duration duration) 은 duration 동안 이벤트도 발생하지 않는걸 기대함과 동시에 duration 만큼 virtual 시간을 앞당긴다.

## backpressure 테스트
- 발행측에서 Overflow 전략,
- 테스트 -> verifyThenAssertThat().hasDroppedElements();