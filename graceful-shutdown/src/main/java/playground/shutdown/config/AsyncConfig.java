package playground.shutdown.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@Configuration
public class AsyncConfig {

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(1);
        taskExecutor.setMaxPoolSize(3);

        // Core Thread 유휴 시간 설정
        taskExecutor.setAllowCoreThreadTimeOut(true); // 기본 값: false
        taskExecutor.setKeepAliveSeconds(5); // 기본 값: 60초
        taskExecutor.setQueueCapacity(0);

        taskExecutor.initialize();

        return taskExecutor;
    }
}
