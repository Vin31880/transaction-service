package com.blue.transactionservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final String API_TITLE = "Transaction Service";
    private final String API_DESCRIPTION = "API Description of the Transaction Service";
    private final String API_VERSION = "1.0";

    @Bean
    public Docket sofaDataConnectorApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.blue.transactionservice.controller"))
                .paths(PathSelectors.ant("/api/v1/**"))
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title(API_TITLE)
                        .description(API_DESCRIPTION)
                        .version(API_VERSION)
                        .build()
                );
    }

}
