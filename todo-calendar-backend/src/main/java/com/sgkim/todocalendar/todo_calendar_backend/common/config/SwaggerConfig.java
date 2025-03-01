package com.sgkim.todocalendar.todo_calendar_backend.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }
    // http://localhost:8080/swagger-ui/index.html
    private Info apiInfo() {
        return new Info()
                .title("Todo-Calendar-Swagger")
                .description("Spring Boot REST API Specifications")
                .version("1.0.0");
    }
}