package playground.nonblockingio.schduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

@Slf4j
public class HotColdSequence {

    public static void main(String[] args) throws InterruptedException {
//        coldSequence();
        hotSequence();
    }

    private static void hotSequence() throws InterruptedException {
        Flux<String> alphabetFlux = Flux.fromArray(new String[]{"a", "b", "c", "d", "e", "f", "g", "h"})
                .delayElements(Duration.ofSeconds(1))
                .share();

        alphabetFlux.subscribe(i -> log.info("first {}", i));
        Thread.sleep(2500);

        log.info("hotSequence finished");
        alphabetFlux.subscribe(i -> log.info("second {}", i));
        Thread.sleep(5000);
    }

    private static void coldSequence() {
        Flux<String> coldFlux = Flux.fromIterable(List.of("KOREA", "CHINA"))
                .map(String::toLowerCase);

        coldFlux
                .subscribe(System.out::println);
        System.out.println("===========================");
        coldFlux.subscribe(System.out::println);
    }
}
