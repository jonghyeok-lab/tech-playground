package playground.nonblockingio.operator.sequence_생성;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
public class Defer {
    public static void main(String[] args) throws InterruptedException {
//        defer1();
        Mono
                .just("Hello")
                .delayElement(Duration.ofSeconds(3))
//                .switchIfEmpty(Mono.just("Hi"))
                .switchIfEmpty(sayDefault())
//                .switchIfEmpty(Mono.defer(() -> Mono.just("Hi")))
                .subscribe(data -> log.info("{}", data));

        Thread.sleep(3500);
    }

    private static Mono<String> sayDefault() {
        return Mono.just("Hi");
    }

    private static void defer1() throws InterruptedException {
        log.info("# start: {}", LocalDateTime.now());
        Mono<LocalDateTime> justMono = Mono.just(LocalDateTime.now());
        Mono<LocalDateTime> deferMono = Mono.defer(() -> Mono.just(LocalDateTime.now()));

        Thread.sleep(2000);

        justMono.subscribe(data -> log.info("justMono: {}", data));
        deferMono.subscribe(data -> log.info("deferMono: {}", data));

        Thread.sleep(2000);
        justMono.subscribe(data -> log.info("justMono2: {}", data));
        deferMono.subscribe(data -> log.info("deferMono2: {}", data));
    }
}
