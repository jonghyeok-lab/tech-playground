package playground.shutdown.config;

import lombok.RequiredArgsConstructor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TaskExecutor taskExecutor;

    @GetMapping("/kill-core-threads")
    public String test() throws InterruptedException {
        ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) taskExecutor;
        System.out.println("executor.getKeepAliveSeconds() = " + executor.getKeepAliveSeconds());
        System.out.println("executor.getCorePoolSize() = " + executor.getCorePoolSize());
        System.out.println("executor.getMaxPoolSize() = " + executor.getMaxPoolSize());

        // Max Thread 까지 실행
        for (int i = 0; i < 3; i++) {
            executor.submit(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        System.out.println("살아있는 쓰레드 수 = " + executor.getPoolSize());

        // 10초간 Blocking
        Thread.sleep(10000);
        System.out.println("10초 뒤 살아있는 쓰레드 수 = " + executor.getPoolSize());

        return "test";
    }
}
