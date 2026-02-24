package playground.nonblockingio.testing;

import reactor.core.publisher.Flux;

public class RecordBasedExample {
    public static Flux<String> getCapitalizedCountry(Flux<String> source) {
        return source
                .map(country -> country.substring(0, 1).toUpperCase() + country.substring(1));
    }
}
