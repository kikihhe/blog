package com.xiaohe.config;


import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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
    @Bean//使用@Bean注入fastJsonHttpMessageConvert
    public HttpMessageConverter fastJsonHttpMessageConverters() {
        //1.需要定义一个Convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");

        SerializeConfig.globalInstance.put(Long.class, ToStringSerializer.instance);

        fastJsonConfig.setSerializeConfig(SerializeConfig.globalInstance);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverters());
    }



}
