package playground.nonblockingio.testing;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RecordBasedExampleTest {
    @Test
    void getCityTest() {
        StepVerifier
                .create(RecordBasedExample.getCapitalizedCountry(
                        Flux.just("korea", "england", "canada")
                ))
                .expectSubscription()
                .recordWith(ArrayList::new)
                .thenConsumeWhile(country -> !country.isEmpty())
                .consumeRecordedWith(countries -> {
                    assertThat(
                            countries.stream()
                                    .allMatch(country -> Character.isUpperCase(country.charAt(0)))
                    );
                })
                .verifyComplete();
    }

}