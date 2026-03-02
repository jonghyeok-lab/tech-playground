package jonghyeok.sse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
                .map(s -> s + " : " + Thread.currentThread().getName());
    }

    @GetMapping(path = "/flux")
    public Flux<String> flux() {
        return Flux.just("Hello", "World", "My", "name", "is")
                .map(s -> s + " : " + Thread.currentThread().getName() + " / ");
    }

    @GetMapping(path = "/flux-dto")
    public Flux<UserResponse> fluxDto() {
        return Flux.just("Hello", "World", "My", "name", "is")
                .map(s -> new UserResponse(Thread.currentThread().getName(), s));
    }



    @GetMapping(path = "/web-flux")
    public Flux<String> webflux() {
        return Flux.just("Hello", "World")
                .doOnNext(s -> System.out.println(
                        s + " thread: " + Thread.currentThread().getName()))
                .map(s -> s + " : " + Thread.currentThread().getName());
    }

    // Flux.interval() 은 어느 스레드에서 처리되는지 확인
    @GetMapping(path = "/stream-flux-interval", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFluxInterval() {
        return Flux.interval(Duration.ofSeconds(1))
                .take(5)
                .map(sequence -> "interval #" + sequence + " : " + Thread.currentThread().getName());
    }

    @GetMapping("/stream-sse-header")
    public ResponseEntity<Flux<ServerSentEvent<String>>> streamEventsWithHeader() {
        Flux<ServerSentEvent<String>> flux = Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(sequence))
                        .event("periodic-event")
                        .data("SSE - " + LocalTime.now())
                        .build());

        return ResponseEntity.ok()
                .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                .body(flux);
    }

    @GetMapping(value = "/stream-sse-header3", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ResponseEntity<String>> streamEventsWithHeader3() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> ResponseEntity.ok()
                        .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                        .body("Hello World"));
    }

    @GetMapping("/stream-sse")
    public Flux<ServerSentEvent<String>> streamEvents() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(sequence))
                        .event("periodic-event")
                        .data("SSE - " + LocalTime.now())
                        .comment("Comment - " + LocalTime.now())
                        .retry(Duration.ofSeconds(1))
                        .build());
    }
}
