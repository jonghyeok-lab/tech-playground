# Hot ? Cold?
- Hot Swap: 컴퓨터 시스템 전원이 켜져있는 상태에서 디스크 같은 장치 교체할 경우 시스템 재시작 안해도 장치를 바로 인식하는 것
- Hot: 처음부터 다시 시작 안해도 됨, 서버/시스템을 다시 가동할 필요 없고, 인터넷 다시 연결할 필요 없음
- Cold: 처음부터 새로 시작, 같은 작업이 반복, 초기화

## Hot/Cold Sequence
- Cold Sequence: Subscriber가 구독할 때마다(Subscriber의 구독 시점이 달라도) 데이터 흐름이 처음부터 다시 시작되는 Sequence
- Hot Sequence: 구독이 발생한 시점 이전에 Publisher로부터 emit된 데이터는 Subscriber가 전달X. 구독 발생한 시점 이후 emit 데이터만 전달받음
  - share(): 멀티캐스트(공유)는 여러 Subscriber 가 원본 Flux 를 공유함(Operator 연산마다 새로운 Flux 가 만들어진다.)

### Reactor 에서 Hot의 두가지 의미
1. Warm up : 최초 구독이 발생하기 전까지 데이터의 emit이 발생하지 않음(일반적, share() 등 최초 구독이 발생했을 때 emit). Warm up 도 Hot Sequence로 취급
2. Hot: 구독 상관없이 데이터가 emit 되는 것.