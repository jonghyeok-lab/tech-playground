package playground.nonblockingio.testing;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class BackpressureTestExampleTest {

    @Test
    void generateNumberTest() {
        StepVerifier
                .create(BackpressureTestExample.generateNumber(), 1) // 데이터 요청 개수
                .thenConsumeWhile(num -> num >= 1)
                .verifyComplete();
    }

    @Test
    void generateNumberTest2() {
        StepVerifier
                .create(BackpressureTestExample.generateNumber(), 1)
                .thenConsumeWhile(num -> num >= 1)
                .expectError()
                .verifyThenAssertThat()
                .hasDiscardedElements();
    }

}