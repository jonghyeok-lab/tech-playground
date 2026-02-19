package playground.nonblockingio.testing;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class SignalBasedExample {
    public static Flux<String> sayHello() {
        return Flux
                .just("Hello World!", "Hello Reactor!");
    }

    public static Flux<Integer> divideByTwo(Flux<Integer> source) {
        return source
                .zipWith(Flux.just(2, 2, 2, 2, 0), (a, b) -> a / b);
    }

    public static Flux<Integer> takeNumber(Flux<Integer> source, long n) {
        return source
                .take(n);
    }
}
