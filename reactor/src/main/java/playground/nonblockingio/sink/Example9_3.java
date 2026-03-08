package playground.nonblockingio.sink;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class Example9_3 {
    public static void main(String[] args) {
        Sinks.One<String> sinkOne = Sinks.one();
        Mono<String> mono = sinkOne.asMono();

        sinkOne.emitValue("Hello Reactor", Sinks.EmitFailureHandler.FAIL_FAST);
        sinkOne.emitValue("Hi Reactor", Sinks.EmitFailureHandler.FAIL_FAST);

        mono.subscribe(System.out::println);
        mono.subscribe(System.out::println);
    }
}
