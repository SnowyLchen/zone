package com.cjl.basic.zone.common.utils.http;

import com.alibaba.fastjson.JSON;
import com.cjl.basic.zone.framework.config.SpringClientHttpRequestInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;


/**
 * RestTemplate Spring配置类
 *
 * @author chen
 */
@Configuration
@ConditionalOnClass({RestTemplate.class, HttpClient.class, JSON.class})
public class RestTemplateConfig {

    /**
     * 连接池的最大连接数默认为0
     */
    @Value("${remote.maxTotalConnect:0}")
    private int maxTotalConnect;
    /**
     * 单个主机的最大连接数
     */
    @Value("${remote.maxConnectPerRoute:200}")
    private int maxConnectPerRoute;
    /**
     * 连接超时默认2s
     */
    @Value("${remote.connectTimeout:120000}")
    private int connectTimeout;
    /**
     * 读取超时默认30s
     */
    @Value("${remote.readTimeout:120000}")
    private int readTimeout;

    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        RestTemplate restTemplate = new RestTemplate(factory);
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();

        //重新设置StringHttpMessageConverter字符集为UTF-8，解决中文乱码问题
        HttpMessageConverter<?> converter = null;
        for (HttpMessageConverter<?> messageConverter : converterList) {
            if (StringHttpMessageConverter.class == messageConverter.getClass()) {
                converter = messageConverter;
                break;
            }
        }
        if (converter != null) {
            converterList.remove(converter);
        }
        converterList.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        converterList.removeIf(c -> c instanceof MappingJackson2HttpMessageConverter);
        converterList.add(new MappingJackson2HttpMessageConverter(jacksonObjectMapperCustomization()));
        restTemplate.getInterceptors().add(new SpringClientHttpRequestInterceptor());

        return restTemplate;
    }

    private ObjectMapper jacksonObjectMapperCustomization() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
        format.setTimeZone(timeZone);
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .timeZone(timeZone)
                .dateFormat(format);
        return builder.build();
    }


    @Bean
    public ClientHttpRequestFactory factory() {
        if (this.maxTotalConnect <= 0) {
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            //单位为ms
            factory.setReadTimeout(this.readTimeout);
            //单位为ms
            factory.setConnectTimeout(this.connectTimeout);
            return factory;
        }
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(this.maxTotalConnect)
                .setMaxConnPerRoute(this.maxConnectPerRoute)
                .build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectTimeout(this.connectTimeout);
        factory.setReadTimeout(this.readTimeout);
        return factory;
    }
}
