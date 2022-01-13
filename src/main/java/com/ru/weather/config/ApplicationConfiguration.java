package com.ru.weather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public Docket weatherApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ru.weather.api")).build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Weather")
                .description("API погоды")
                .license("License")
                .licenseUrl("https://unlicense.org/")
                .termsOfServiceUrl("")
                .version(getClass().getPackage().getImplementationVersion())
                .build();
    }


}
