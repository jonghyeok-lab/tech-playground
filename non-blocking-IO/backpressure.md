# Backpressure
- Publisher 가 끊임없이 emit 하는 데이터들을 제어하여 데이터 처리에 과부하 걸리지 않게 하는 것.

#### 필요 이유
1. Subscriber 의 처리 결과에 관계없이 Publisher 는 데이터를 계속 emit 한다
2. emit 된 데이터는 대기 중이 되어 오버플로 발생하거나 시스템 다운

### Reactor에서 Backpressure 처리 방식
1. 데이터 개수 제어(Subscriber 가 Publisher 에게 데이터 개수 요청)
   - request(long n)
2. backpressure 전략
   - IGNORE: Backpressure 적용 X
   - ERROR: Downstream으로 전달할 데이터가 버퍼에 가득 찰 경우, Exception(IllegalStateException) 발생
   - DROP: Downstream으로 전달할 데이터가 버퍼에 가득 찰 경우, 버퍼 밖에서 대기하는 가장 먼저 emit된 데이터를 하나씩 Drop
   - LATEST
     - Downstream으로 전달할 데이터가 버퍼에 가득 찬다.
     - 밖에서 emit 된 데이터들이 버퍼에 들어가려고 대기한다.
     - 버퍼에 데이터가 들어갈 수 있을 때, 대기하는 모든 데이터를 Drop 하고 최근에 emit된 데이터부터 버퍼에 채우는 전략
   - BUFFER: Downstream으로 전달할 데이터가 버퍼에 가득 찰 경우, 버퍼 안에 있는 데이터부터 Drop 시키는 전략

