# Sequence 생성을 위한 Operator
- just(): Hot Publisher 이기 때문에 구독 여부의 상관 없이 데이터를 emit 한다. 구독이 발생하면 emit된 데이터를 replay해서 emit
- defer(): 구독이 발생하기 전까지 데이터의 emit을 지연시킨다.
- using(): 파라미터로 전달받은 resource를 emit하는 Flux를 생성
- generate(): 프로그래밍 방식으로 Signal 이벤트를 발생, 동기적으로 데이터를 하나씩 순차적으로 emit
- create(): generate() 와 달리 여러 건의 데이터를 비동기적으로 emit

