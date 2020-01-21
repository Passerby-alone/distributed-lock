package com.pjg.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author stranger_alone
 * @description TODO
 * @date 2020/1/12 下午12:31
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(getApiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.pjg.demo.controller"))
                    .paths(PathSelectors.any())
                    .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("分布式锁")
                .description("利用zookeeper, redis构建分布式锁demo")
                .version("1.0")
                .build();
    }
}
