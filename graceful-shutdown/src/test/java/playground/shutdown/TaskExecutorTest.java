package playground.shutdown;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TaskExecutorTest {

    @Test
    @DisplayName("쓰레드 풀 생성 및 소멸 테스트 (Core 포함)")
    void threadPoolLifecycleTest() throws InterruptedException {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(1);
        taskExecutor.setMaxPoolSize(3);
        taskExecutor.setQueueCapacity(0);

        taskExecutor.setAllowCoreThreadTimeOut(true);
        taskExecutor.setKeepAliveSeconds(5);
        taskExecutor.initialize();

        // 1. 초기 상태 확인 (AllowCoreThreadTimeOut이 true이므로 처음엔 0일 수 있음)
        System.out.println("=== 시작 상태 ===");
        printStatus(taskExecutor);

        // 2. 작업 3개 동시 실행 (Core 1 + Max 2)
        // QueueCapacity가 0이므로 큐에 쌓이지 않고 바로 Max까지 생성됨
        int taskCount = 3;
        CountDownLatch latch = new CountDownLatch(taskCount);

        for (int i = 0; i < taskCount; i++) {
            taskExecutor.execute(() -> {
                try {
                    Thread.sleep(2000); // 2초간 작업
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }

        System.out.println("=== 작업 투입 직후 ===");
        printStatus(taskExecutor);
        // 큐가 0이므로 즉시 MaxPoolSize인 3까지 늘어나야 함
        assertThat(taskExecutor.getPoolSize()).isEqualTo(3);

        // 3. 작업 완료 대기 (2초 작업이 끝날 때까지)
        latch.await(5, TimeUnit.SECONDS);
        System.out.println("=== 작업 완료 직후 (유휴 시작) ===");
        printStatus(taskExecutor);

        // 4. KeepAliveSeconds(5초) + 여유 시간(2초) 대기
        System.out.println("5초(KeepAlive) 대기 중...");
        Thread.sleep(7000);

        System.out.println("=== KeepAlive 경과 후 ===");
        printStatus(taskExecutor);

        // 5. 결과 검증: AllowCoreThreadTimeOut(true) 이므로 0이 되어야 함
        assertThat(taskExecutor.getPoolSize()).isEqualTo(0);
    }

    private void printStatus(ThreadPoolTaskExecutor taskExecutor) {
        System.out.println("현재 PoolSize: " + taskExecutor.getPoolSize());
        System.out.println("현재 ActiveCount: " + taskExecutor.getActiveCount());
        System.out.println("-------------------------");
    }
}
