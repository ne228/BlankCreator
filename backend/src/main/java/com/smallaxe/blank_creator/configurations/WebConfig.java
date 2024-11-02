package com.smallaxe.blank_creator.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${vue_url}")
    private String vueUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Разрешить все запросы
                .allowedOrigins(vueUrl, "http://localhost:5173") // Разрешить этот источник
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешить методы
                .allowedHeaders("*") // Разрешить все заголовки
                .allowCredentials(true); // Разрешить отправку куки
    }
}
