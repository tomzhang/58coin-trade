package com.coin58.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author coin58 - 2018/4/4.
 */
@Configuration
public class AppConfig {
    @Bean("api-executor")
    public Executor executor() {
        return new ThreadPoolExecutor(10, 10, 10000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(10000),
            new ThreadPoolExecutor.DiscardPolicy());
    }
}
