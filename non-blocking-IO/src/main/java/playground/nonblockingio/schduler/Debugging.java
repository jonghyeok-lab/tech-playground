package playground.nonblockingio.schduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@Slf4j
public class Debugging {
    private static Map<String, String> fruits = new HashMap<>();

    static {
        fruits.put("apple", "사과");
        fruits.put("orange", "오렌지");
        fruits.put("banana", "바나나");
        fruits.put("pear", "배");
    }

    public static void main(String[] args) throws InterruptedException {
//        checkpoint();
        logOperator();
    }

    private static void logOperator() throws InterruptedException {
        Flux
                .fromArray(new String[]{"BANANAS", "APPLES", "PEARS", "MELLONS"})
//                .subscribeOn(Schedulers.boundedElastic())
//                .publishOn(Schedulers.parallel())
                .map(String::toLowerCase)
                .map(fruit -> fruit.substring(0, fruit.length() - 1))
//                .checkpoint()
                .log("Fruit.Substring", Level.FINE)
//                .log()
                .map(fruit -> fruits.get(fruit))
//                .checkpoint()
//                .map(fruit -> "맛있는 " + fruit)
//                .checkpoint()
                .subscribe(
                        log::info,
                        error -> log.error("onError:", error)
                );

//        Thread.sleep(1000);
    }

    private static void checkpoint() throws InterruptedException {
        /**
         * java.lang.NullPointerException: The mapper [playground.nonblockingio.schduler.Debugging$$Lambda$33/0x000001999c099e30] returned a null value.
         * 	at reactor.core.publisher.FluxMapFuseable$MapFuseableSubscriber.onNext(FluxMapFuseable.java:115)
         * 	Suppressed: The stacktrace has been enhanced by Reactor, refer to additional information below:
         * Assembly trace from producer [reactor.core.publisher.FluxMapFuseable] :
         * 	reactor.core.publisher.Flux.map(Flux.java:6631)
         * 	playground.nonblockingio.schduler.Debugging.main(Debugging.java:31)
         * Error has been observed at the following site(s):
         * 	*__Flux.map ⇢ at playground.nonblockingio.schduler.Debugging.main(Debugging.java:31)
         * 	|_ Flux.map ⇢ at playground.nonblockingio.schduler.Debugging.main(Debugging.java:32)
         */
        Hooks.onOperatorDebug();

        Flux
                .fromArray(new String[]{"BANANAS", "APPLES", "PEARS", "MELLONS"})
                .subscribeOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.parallel())
                .map(String::toLowerCase)
                .map(fruit -> fruit.substring(0, fruit.length() - 1))
                .map(fruit -> fruits.get(fruit))
                .map(fruit -> "맛있는 " + fruit)
                .subscribe(
                        log::info,
                        error -> log.error("onError:", error)
                );

        Thread.sleep(1000);
    }
}
