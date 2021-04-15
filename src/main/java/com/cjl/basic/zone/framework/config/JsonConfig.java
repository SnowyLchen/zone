package com.cjl.basic.zone.framework.config;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author chen
 */
@Configuration
public class JsonConfig {

    @Bean
    public HttpMessageConverters customConverters() {
        Collection<HttpMessageConverter<?>> converters = new ArrayList<>();
        // 创建 convert 消息转换对象
//        EmojiMappingJackson2HttpMessageConverter emojiMappingJackson2HttpMessageConverter = new EmojiMappingJackson2HttpMessageConverter();
//        converters.add(emojiMappingJackson2HttpMessageConverter);

        return new HttpMessageConverters(true, converters);
    }


//    @Bean
//    public FilterRegistrationBean<EmojiOncePerRequestFilter> emojiOncePerRequestFilterFilterRegistrationBean() {
//        FilterRegistrationBean<EmojiOncePerRequestFilter> registration = new FilterRegistrationBean<>();
//        //注册拦截器
//        registration.setFilter(new EmojiOncePerRequestFilter());
//        //拦截的URL
//        registration.addUrlPatterns("/*");
//        registration.setName("emojiEncodingFilter");
//        //设置该拦截器执行的顺序
//        registration.setOrder(1);
//        return registration;
//
//    }
}
