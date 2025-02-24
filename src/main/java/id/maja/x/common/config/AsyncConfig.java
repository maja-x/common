package id.maja.x.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class AsyncConfig {
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        if (supportsVirtualThreads()) {
            // Configure for virtual threads if supported
            return Executors.newWorkStealingPool();
        } else {
            // Fall back to standard thread pool
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(5);
            executor.setMaxPoolSize(10);
            executor.setQueueCapacity(100);
            executor.setThreadNamePrefix("MajaxAsyncThread-");
            executor.initialize();
            return executor;
        }
    }

    private boolean supportsVirtualThreads() {
        String javaVersion = System.getProperty("java.version");
        String majorVersion = javaVersion.split("\\.")[0];
        return Integer.parseInt(majorVersion) >= 19;
    }
}
