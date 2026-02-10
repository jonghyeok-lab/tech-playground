# 스케줄러
- subscribeOn: 구독이 발생한 직후 실행될 스레드를 지정하는 연산자
- publishOn: 해당 메서드 기준으로 Downstream의 실행 스레드를 변경
- parallel: Round Robin 방식으로 논리 코어 개수만큼 병렬 실행, 데이터를 논리 CPU 코어 수에 맞게 분산하는 역할이므로 runOn 과 함께 사용해야함.