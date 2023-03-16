package com.xiaohe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@SpringBootApplication
@EnableTransactionManagement
public class BlogAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class, args);
    }
    @Bean(name = "threadPoolExecutor")
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(2, 5, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5), new ThreadPoolExecutor.CallerRunsPolicy());
    }

} 