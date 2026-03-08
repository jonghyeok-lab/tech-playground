package playground.nonblockingio.sink;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Slf4j
public class Sinks_many {
    public static void main(String[] args) {
        // Sinks Unicast
//        Sinks.Many<Integer> unicastSink = Sinks.many().unicast().onBackpressureBuffer();
//        Flux<Integer> fluxView = unicastSink.asFlux();
//
//        unicastSink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
//        unicastSink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);
//
//        fluxView.subscribe(data -> log.info("# subscribe1: {}", data));
//
//        unicastSink.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);
//
//        fluxView.subscribe(data -> log.info("# subscribe2: {}", data));

        /**
         * Sinks Many
         */
//        Sinks.Many<Integer> multiSinks = Sinks.many().multicast().onBackpressureBuffer();
//        Flux<Integer> fluxView = multiSinks.asFlux();
//
//        multiSinks.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
//        multiSinks.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);
//
//        fluxView.subscribe(data -> log.info("# subscribe1: {}", data));
//        fluxView.subscribe(data -> log.info("# subscribe2: {}", data));
//
//        multiSinks.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);

        /**
         * Sinks replay
         */
        Sinks.Many<Integer> replaySink = Sinks.many().replay().limit(2);
        Flux<Integer> fluxView = replaySink.asFlux();

        replaySink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        replaySink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);
        replaySink.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);

        fluxView.subscribe(data -> log.info("# subscribe1: {}", data));

        replaySink.emitNext(4, Sinks.EmitFailureHandler.FAIL_FAST);

        fluxView.subscribe(data -> log.info("# subscribe2: {}", data));
    }
}
