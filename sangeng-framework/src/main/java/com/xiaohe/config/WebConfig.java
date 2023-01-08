package com.xiaohe.config;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-03 10:38
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许所有请求跨域
        registry.addMapping("/**")
                // 设置允许所有跨域的域名
                .allowedOriginPatterns("*")
                // 允许cookie
                .allowCredentials(true)
                // 设置允许的请求方法
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                // 设置允许的Header属性
                .allowedHeaders("*")
                // 跨域允许时间
                .maxAge(3600);
    }


}
