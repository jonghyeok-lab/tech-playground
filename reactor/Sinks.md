# Sinks
- Publisher와 Subscriber의 기능을 모두 지녔다.
- 데이터를 emit하는 Sinks는 Thread-safe하다.
- Sinks.One 은 한 건의 데이터를 emit 한다
- Sinks.Many 는 여러 건의 데이터를 emit 한다
- Sinks.Many의 UnicastSpec 은 단 하나의 Subscriber에게만 데이터를 emit 한다.
- Sinks.Many의 MulticastSpec 은 하나 이상의 Subscriber에게 데이터를 emit 한다.
- Sinks.Many의 MulticastReplaySpec은 emit된 데이터 중에서 특성 시점으로 되돌린 데이터부터 emit하낟.