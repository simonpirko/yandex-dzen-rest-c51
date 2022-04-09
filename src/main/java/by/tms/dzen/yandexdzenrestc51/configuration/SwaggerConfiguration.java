package by.tms.dzen.yandexdzenrestc51.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableWebMvc
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("by.tms.dzen.yandexdzenrestc51"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Yandex Dzen API",
                "Yandex Dzen API by TMS-c51 ",
                "1.0.0 ",
                "http://localhost:8080",
                new Contact("Contact the developer", "http://localhost:8080", "admin@localhost.by"),
                "Find out more about Yandex Dzen", "https://zen.yandex.ru/", Collections.emptyList());
    }
}
