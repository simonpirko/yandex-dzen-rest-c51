package by.tms.dzen.yandexdzenrestc51.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("by.tms.dzen.yandexdzenrestc51.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(List.of(apiKey()));
    }

    private ApiKey apiKey() {
        return new ApiKey("apiKey", "Authorization", "header"); //`apiKey` is the name of the APIKey, `Authorization` is the key in the request header
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Yandez DZEN REST API Document")
                .description("TMS C-51 REST API for YANDEX DZEN")
                .contact(new Contact("GEMUZKM", "", "gemuzkm@gmail.com"))
                .version("1.0")
                .build();
    }
}
