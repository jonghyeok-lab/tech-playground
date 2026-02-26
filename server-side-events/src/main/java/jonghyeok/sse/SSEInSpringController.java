package jonghyeok.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Spring 에서 SSE 구현 방법
 */
@Slf4j
@RestController
public class SSEInSpringController {
    @GetMapping(path = "/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFlux() {
        return Flux.just("Hello", "World")
                .map(sequence -> "Flux - " + LocalTime.now());
    }

    @GetMapping("/stream-sse")
    public Flux<ServerSentEvent<String>> streamEvents() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> ServerSentEvent.<String> builder()
                        .id(String.valueOf(sequence))
                        .event("periodic-event")
                        .data("SSE - " + LocalTime.now().toString())
                        .build());
    }
}
