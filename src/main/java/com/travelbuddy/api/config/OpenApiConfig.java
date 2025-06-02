package com.travelbuddy.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI travelBuddyOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TravelBuddy API")
                        .description("API for recommending travel destinations based on age group and budget")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("TravelBuddy Team")
                                .url("https://travelbuddy.com")
                                .email("contact@travelbuddy.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local development server")
                ));
    }
} 