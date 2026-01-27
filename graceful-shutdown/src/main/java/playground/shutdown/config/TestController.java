package playground.shutdown.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final ThreadPoolTaskExecutor taskExecutor1;
    private final ThreadPoolTaskExecutor taskExecutor2;


    @GetMapping("/tasks")
    public String test() throws InterruptedException {
//        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) taskExecutor1;
        log.info("비동기 작업 제출 (80초 소요 예상)...");
        taskExecutor1.submit(() -> {
            try {
                for (int i = 1; i <= 80; i++) {
                    Thread.sleep(1000); // 1초씩 30번 대기
                    log.info("비동기 작업 진행 중: {}초 경과", i);
                }
                log.info("비동기 작업 완료!");
            } catch (InterruptedException e) {
                log.warn("비동기 작업이 강제 종료(Interrupted) 되었습니다!");
            }
        });

        return "작업이 제출되었습니다. 이제 서버를 종료(Ctrl+C)하여 로그를 확인하세요.";
    }

    @GetMapping("/tasks-2")
    public String test2() throws InterruptedException {
//        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) taskExecutor2;
        log.info("비동기 작업 제출 (30초 소요 예상)...");
        taskExecutor2.submit(() -> {
            try {
                for (int i = 1; i <= 30; i++) {
                    Thread.sleep(1000); // 1초씩 30번 대기
                    log.info("비동기 작업 진행 중: {}초 경과", i);
                }
                log.info("비동기 작업 완료!");
            } catch (InterruptedException e) {
                log.warn("비동기 작업이 강제 종료(Interrupted) 되었습니다!");
            }
        });

        return "작업이 제출되었습니다. 이제 서버를 종료(Ctrl+C)하여 로그를 확인하세요.";
    }
}
