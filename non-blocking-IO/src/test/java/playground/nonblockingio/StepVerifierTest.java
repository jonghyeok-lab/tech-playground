package playground.nonblockingio;

import org.junit.jupiter.api.Test;
import playground.nonblockingio.testing.SignalBasedExample;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;

public class StepVerifierTest {

    @Test
    void onSignalEvent() {
        StepVerifier
                .create(Mono.just("Hello Reactor!")) // 테스트 대상 Sequence 생성
                .expectNext("Hello Reactor!") // expectXXXX() 를 통해 예상되는 Signal 기댓값 평가
                .expectComplete() //
                .verify();
    }

    @Test
    void sayHelloTest() {
        StepVerifier.create(SignalBasedExample.sayHello())
                .expectNext("Hello World!")
                .expectNext("Hello Reactor!")
                .expectNextCount(0)
                .expectComplete()
                .verify();

        StepVerifier
                .create(SignalBasedExample.sayHello())
                .expectSubscription()
                .as("expect Subscription")
                .expectNext("Hello World!")
                .as("expect Hello World")
                .expectNext("Hello Reactor!")
                .as("expect~~ Hello Reactor")
                .verifyComplete();
    }

    @Test
    void divideByTwoTest() {
        Flux<Integer> source = Flux.just(2, 4, 6, 8, 10);

        StepVerifier
                .create(SignalBasedExample.divideByTwo(source))
                .expectSubscription()
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectError(ArithmeticException.class)
                .verify();
    }

    @Test
    void takeNumber() {
        Flux<Integer> source = Flux.range(0, 1000);
        StepVerifier
                .create(SignalBasedExample.takeNumber(source, 500),
                        StepVerifierOptions.create().scenarioName(
                                "Verify from 0 to 499"
                        ))
                .expectSubscription()
                .expectNext(0)
                .expectNextCount(498)
                .expectNext(499)
                .verifyComplete();
    }
}
