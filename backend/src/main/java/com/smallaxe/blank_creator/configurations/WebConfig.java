package com.smallaxe.blank_creator.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Разрешить все запросы
                .allowedOrigins("http://194.58.66.113:5173") // Разрешить этот источник
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешить методы
                .allowedHeaders("*") // Разрешить все заголовки
                .allowCredentials(true); // Разрешить отправку куки
    }
}
