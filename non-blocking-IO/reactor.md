### ❗❗ **스프링으로 시작하는 리액티브 프로그래밍**을 읽고 정리한 내용입니다.
# 리액티브 프로그래밍 

## 리액티브 목표
- 리액티브 시스템 -> 어떤 상황이나 이벤트에 대해 즉각 반응 해서 즉각 응답 하는 시스템
- 리액티브 선언문
  - MEANS: 비동기 메시지 기반 통신
  - FORM:
    - 시스템의 작업량이 변화해도 일정한 응답을 유지(탄력성)
    - 시스템 장애가 발생해도 응답성 유지(회복성) -> 비동기 메시지 기반 통신을 통해 달성함
- 리액티브는 선언형 프로그래밍
  - 동작을 구체적으로 명시하지 않고 목표만 선언한다.(메서드 체인)

## 리액티브 프로그래밍(개념/패러다임)
- Publisher: 데이터 제공자
- Consumer: Publisher가 제공한 데이터를 사용하는 소비자(구독자)
- Data Source: Publisher의 입력으로 들어오는 원천 데이터 자체
  - Data Stream: Source 랑 같이 봐도 무방하지만. Publisher의 입력으로 들어오는 데이터의 형태
- Operator: 데이터 가공 처리자(데이터 필터링, 데이터 변환)

## 리액티브 스트림즈(Streams) (프로그래밍을 구현하기 위한 표준 명세)
- Publisher: 데이터를 생성하고 통지(발행, 게시, 방출)
  - `public void subscribe(Subscribe<? super T> s);`
- Subscriber: 구독한 Publisher 부터 데이터를 받아서 처리
  - `public void onSubscribe(Subscription s);`
  - `public void onNext(T t);`
  - `public void onError(Throwable t);`
  - `public void onComplete();`
- Subscription: Publisher 에 요청할 데이터 개수를 지정하고, 데이터의 구독을 취소하는 역할
  - `public void request(long n);`
  - `public void cancel();`
- Processor: Publisher와 Subscriber의 기능을 모두 가지고 있다.

