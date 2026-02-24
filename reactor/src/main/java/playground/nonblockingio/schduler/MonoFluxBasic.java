package playground.nonblockingio.schduler;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxBasic {
    public static void main(String[] args) {
//        concatWith();
        concatFlux();
    }

    private static void concatFlux() {
        Flux.concat(
                        Flux.just("Hi", "My", "Name"),
                        Flux.just("is", "jonhg", "hyeok"),
                        Flux.just("Nice", "eet", "you")
                )
                .collectList()
                .subscribe(System.out::println);

    }

    private static Disposable concatWith() {
        return Mono.justOrEmpty("Name")
                .concatWith(Mono.justOrEmpty("Surname"))
                .subscribe(System.out::println);
    }
}
