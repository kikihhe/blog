package com.xiaohe;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SanGengBlogApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(SanGengBlogApplication.class, args);

    }
}
