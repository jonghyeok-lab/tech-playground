package playground.nonblockingio.testing;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class TimeBasedExampleTest {
    @Test
    void getCOVID19CounteTest() {
        StepVerifier
                // VirtualTimeScheduler 라는 가상 스케줄러의 제어를 받도록 해준다.
                .withVirtualTime(() -> TimeBasedExample.getCOVID19Count(
                        Flux.interval(Duration.ofHours(1)).take(1)
                ))
                .expectSubscription()
                // 구독에 대한 기댓값을 평가하고 난 후 then() 을 통해 후속 작업을 하게 한다
                .then(() -> VirtualTimeScheduler
                        .get()
                        // 시간을 1시간 앞당긴다
                        .advanceTimeBy(Duration.ofHours(1)))
                .expectNextCount(7)
                .verifyComplete();
    }

    @Test
    void getCOVID19CountTest2() {
        StepVerifier
                .create(TimeBasedExample.getCOVID19Count(
                        Flux.interval(Duration.ofMinutes(1)).take(1)
                ))
                .expectSubscription()
                .expectNextCount(7)
                .expectComplete()
                // 3초 이내로 기댓값의 평가가 끝나ㄱ지 않으면 시간 초과로 간주
                .verify(Duration.ofSeconds(3));
    }

    @Test
    void getVoteCountTest() {
        StepVerifier
                .withVirtualTime(() -> TimeBasedExample.getVoteCount(
                        Flux.interval(Duration.ofMinutes(1))
                ))
                .expectSubscription()
                .expectNoEvent(Duration.ofMinutes(1)) // 이벤트를 기대함과 동시에 지정한 시간만큼 앞당긴다.
                .expectNoEvent(Duration.ofMinutes(1))
                .expectNoEvent(Duration.ofMinutes(1))
                .expectNoEvent(Duration.ofMinutes(1))
                .expectNoEvent(Duration.ofMinutes(1))
                .expectNextCount(5)
                .expectComplete()
                .verify();
    }
}
