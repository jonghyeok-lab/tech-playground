# Reactor 의 Testing

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
