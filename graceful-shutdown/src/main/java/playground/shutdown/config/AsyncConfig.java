package playground.shutdown.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Configuration
public class AsyncConfig {

    @Bean
    public ThreadPoolTaskExecutor taskExecutor1() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(1);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(30);

        taskExecutor.setThreadNamePrefix("Async1-");

        taskExecutor.initialize();

        return taskExecutor;
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor2() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(1);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(15);

        taskExecutor.setThreadNamePrefix("Async2-");

        taskExecutor.initialize();

        return taskExecutor;
    }

    @Component
    public static class MyPhase implements SmartLifecycle {
        private volatile boolean running = false;

        @Override
        public void start() {
            log.info("HighPhaseComponent 시작");
            running = true;
        }

        @Override
        public void stop() {
            log.info("[Phase 1000 - Bean1] stop() 시작");
            try {
                Thread.sleep(15000); // 15초
            } catch (InterruptedException e) {}
            log.info("[Phase 1000 - Bean1] stop() 종료");
        }

        @Override
        public int getPhase() {
            return 1000;
        }
        @Override
        public boolean isRunning() {
            return running;
        }
    }

    @Component
    static public class LowPhaseComponent implements SmartLifecycle {

        private volatile boolean running = false;

        @Override
        public void start() {
            log.info("LowPhaseComponent 시작");
            running = true;
        }

        @Override
        public boolean isRunning() {
            return running;
        }

        @Override
        public void stop() {
            log.info("[Phase 1000 - Bean2] stop() 시작");
            try {
                Thread.sleep(3000); // 3초
            } catch (InterruptedException e) {}
            log.info("[Phase 1000 - Bean2] stop() 종료");
        }

        @Override
        public void stop(Runnable callback) {
            log.info("[Phase 1000 - Bean2] stop() 시작");
            try {
                Thread.sleep(3000); // 3초
            } catch (InterruptedException e) {}
            log.info("[Phase 1000 - Bean2] stop() 종료");
        }

        @Override
        public int getPhase() {
            return 1000;
        }
    }
}
