package com.example.chatapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
                registry.addMapping("/api/**") // Позволява CORS за всички ендпойнти
                        .allowedOrigins("http://localhost:5173") // Позволен фронтенд
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Позволени методи
                        .allowCredentials(true);
            }
        };
    }
}
