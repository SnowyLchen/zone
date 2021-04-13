package com.cjl.basic.zone.framework.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2的接口配置
 *
 * @author wangsen
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * 系统基础配置
     */
    @Autowired
    private ZoneConfig zoneConfig;

    /**
     * 创建API
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 详细定制
                .apiInfo(apiInfo())
                .select()
                // 指定当前包路径com.uustop.spider.controller
                //.apis(RequestHandlerSelectors.basePackage("com.uustop.project.tool.swagger"))
//                .apis(RequestHandlerSelectors.basePackage("com.uustop.spider.controller"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo() {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                .title("标题：爬虫接口文档")
                .description("描述：api对接接口文档")
                .contact(new Contact(zoneConfig.getName(), null, null))
                .version("版本号:" + zoneConfig.getVersion())
                .build();
    }
}
