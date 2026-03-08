package playground.nonblockingio.operator.sequence_생성;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

@Slf4j
public class Generate {
    public static void main(String[] args) {
//        generateInteger();
//        generateGugudan3();

    }

    private static Disposable generateGugudan3() {
        return Flux.generate(
                        () -> Tuples.of(3, 1),
                        (state, sink) -> {
                            sink.next(state.getT1() + " * " + state.getT2() + " = " + state.getT1() * state.getT2());
                            if (state.getT2() == 9) {
                                sink.complete();
                            }
                            return Tuples.of(state.getT1(), state.getT2() + 1);
                        },
                        state -> log.info("구구단 {}단 종료!", state.getT1()))
                .subscribe(System.out::println);
    }

    private static void generateInteger() {
        Flux
                .generate(() -> 0, (state, sink) -> {
                    sink.next(state); // state(상태 값)을 emit
                    if (state == 5) {
                        sink.complete();
                    }
                    return ++state;
                })
                .subscribe(data -> log.info("{}", data));
    }
}
