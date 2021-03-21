package com.cjl.basci.zone.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hd_zhu
 */
@Component
public class CorsConfig implements WebMvcConfigurer {

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").
                        //允许跨域的域名，可以用*表示允许任何域名使用
                                allowedOrigins("*").
                        //允许任何方法（post、get等）
                                allowedMethods("*").
                        //允许任何请求头
                                allowedHeaders("*").
                        //带上cookie信息
                                allowCredentials(true).
                        //带上cookie信息
                                exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L);
            }
        };
    }
}
