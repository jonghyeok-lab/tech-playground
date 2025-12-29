package playground.nonblockingio.basic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Optional;

class ReactorBasicTest {

    @DisplayName("Mono객체는 0~1개, Flux객체는 1~N개의 아이템을 가진다.")
    @Test
    void fluxAndMonoBasicTest() {
        Mono<String> mono1 = Mono.just("mono1");
        mono1.subscribe(item -> System.out.println("mono item: " + item));
        StepVerifier.create(mono1)
                .expectNextCount(1)
                .verifyComplete();

        Mono<Object> emptyMono = Mono.justOrEmpty(Optional.empty());
        emptyMono.subscribe(item -> System.out.println("empty item: " + item));
        StepVerifier.create(emptyMono)
                .expectComplete()
                .verify();

        Flux<String> fluxItem = Flux.just("item1", "item2", "item3");
        fluxItem.subscribe(item -> System.out.println("flux item: " + item));
        StepVerifier.create(fluxItem)
                .expectNextCount(3)
                .verifyComplete();
    }
}