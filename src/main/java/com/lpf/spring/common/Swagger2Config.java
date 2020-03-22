package com.lpf.spring.common;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2的配置类
 * 启动后访问：http://{root-path}/swagger-ui.html
 *
 * @author lipengfei
 * @create 2019-04-17 10:05
 **/
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Value("${swagger2.enable}")
    private boolean enable;

    @Bean("GirlApis")
    public Docket girlApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户模块")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.regex("/girl.*"))
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }


    @Bean("ZCustomApis")
    public Docket customApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("客户模块")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.regex("/custom.*"))
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("lpf系统平台接口文档")
                .description("提供子用户模块/客户模块的文档")
                .termsOfServiceUrl("https://xingtian.github.io/trace.github.io/")
                .version("1.0")
                .build();
    }

}
