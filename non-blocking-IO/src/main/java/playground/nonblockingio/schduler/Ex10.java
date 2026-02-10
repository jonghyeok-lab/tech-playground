package playground.nonblockingio.schduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Ex10 {

    public static void main(String[] args) throws InterruptedException {
//        subscribeOn();
//        publishOn();
//        parallel();
//        twoPublishOn();

        Thread.sleep(5000);

    }

    private static void twoPublishOn() {
        Flux.fromArray(new Integer[]{1,2,3,4,5})
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(data -> log.info("doOnNext fromArray: {}", data))
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("doOnNext filter: {}", data))
                .subscribeOn(Schedulers.parallel())
                .map(data -> data * 2)
                .doOnNext(data -> log.info("doOnNext map: {}", data))
                .subscribe(data -> log.info("doOnNext subscribe: {}", data));
    }

    private static void parallel() {
        Flux.fromArray(new Integer[]{1,2,3,4,5,6,7,8,9,10})
                .parallel() // CPU 논리 코어 수에 맞게
                .runOn(Schedulers.parallel())
                .subscribe(data -> log.info("subscribe: {}", data));
    }

    // publishOn 아래부터만 Downstream의 실행 스레드 제어
    private static void publishOn() {
        Flux.fromArray(new Integer[]{1,2,3})
                .doOnNext(data -> log.info("doOnNext: {}", data))
                .doOnSubscribe(subscription -> log.info("Subscribed"))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> log.info("onNext: {}", data));
    }

    // subscribeOn 은 ~에서 구독을 시작한다는 의미로 emit 부터 쓰레드 제어
    private static void subscribeOn() {
        Flux.fromArray(new Integer[]{1, 2, 3})
                .doOnNext(data -> log.info("doOnNext: " + data))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSubscribe(subcription -> log.info("doOnSubscribe: " + subcription)) // 구독이 발생한 시점에 추가적인 어떤 처리 main쓰레드
                .subscribe(data -> log.info("subscribe: " + data));
    }
}
