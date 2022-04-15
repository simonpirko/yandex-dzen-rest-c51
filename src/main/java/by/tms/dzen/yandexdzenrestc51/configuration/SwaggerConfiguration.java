package by.tms.dzen.yandexdzenrestc51.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("by.tms.dzen.yandexdzenrestc51.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Yandex Dzen API",
                "Yandex Dzen API by TMS-C51 ",
                "1.0.0 ",
                "http://localhost:8080",
                new Contact("Contact the developer", "http://localhost:8080", "admin@localhost.by"),
                "Find out more about Yandex Dzen", "https://zen.yandex.ru/", Collections.emptyList());
    }
}
