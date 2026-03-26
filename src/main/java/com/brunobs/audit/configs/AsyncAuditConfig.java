package com.brunobs.audit.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncAuditConfig {

    @Bean(name = "auditExecutor")
    public ThreadPoolTaskExecutor auditExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(1000);

        executor.setThreadNamePrefix("audit-");

        executor.setTaskDecorator(new ContextCopyingDecorator());

        executor.initialize();

        return executor;
    }
}