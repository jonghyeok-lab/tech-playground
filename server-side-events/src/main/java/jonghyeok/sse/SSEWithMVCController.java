package jonghyeok.sse;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
public class SSEWithMVCController {

    @GetMapping(value = "/mvc/sse-emitter")
    public SseEmitter sseEmitter() throws IOException {
        SseEmitter emitter = new SseEmitter();
        emitter.send(SseEmitter.event()
                .id("id-1")
                .data("Hello World")
                .name("event 필드")
                .comment("event 코멘트")
                .reconnectTime(10000));

        emitter.complete();
        return emitter;
    }

    /**
     * Last-Event-ID 동작 흐름 데모
     *
     * 1. 최초 연결: 이벤트 1~5 전송 후 강제 종료 (completeWithError)
     * 2. 브라우저 자동 재연결: Last-Event-ID: 5 헤더 포함
     * 3. 서버: 헤더를 읽고 이벤트 6~10부터 이어서 전송 후 정상 완료
     */
    @GetMapping(value = "/mvc/last-event-id", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> lastEventIdDemo(
            @RequestHeader(value = "Last-Event-ID", required = false) Integer lastEventId
    ) {
        SseEmitter emitter = new SseEmitter(10L);

        int startFrom = (lastEventId != null) ? (lastEventId) + 1 : 1;
        boolean isReconnect = lastEventId != null;

        log.info("===== 연결 요청 =====");
        log.info("Last-Event-ID 헤더: {}", lastEventId != null ? lastEventId : "(없음 - 최초 연결)");
        log.info("이벤트 전송 시작 번호: {}", startFrom);

        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(() -> {
            try {
                for (int i = startFrom; i <= 10; i++) {
                    String connectionLabel = isReconnect ? "재연결" : "최초연결";

                    emitter.send(SseEmitter.event()
                            .id(String.valueOf(i))
                            .name("sse-event")
                            .data("이벤트 #" + i + " [" + connectionLabel + "]")
                            .reconnectTime(2000)); // 끊기면 2초 후 재연결

                    log.info("이벤트 전송 완료: #{}", i);
                    Thread.sleep(1000);

                    // 최초 연결에서 5번 이후 강제 종료 → 브라우저 재연결 유도
                    if (i == 5 && !isReconnect) {
                        log.warn("5번 이벤트 이후 강제 종료 → 브라우저가 Last-Event-ID: 5 를 헤더에 담아 재연결합니다");
                        emitter.completeWithError(new RuntimeException("재연결 유도를 위한 강제 종료"));
                        return;
                    }
                }

                log.info("모든 이벤트 전송 완료 → 정상 종료");
                emitter.complete();

            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return ResponseEntity.ok(emitter);
    }
}