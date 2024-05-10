package com.example.calendarbe;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000") // Cho phép truy cập từ origin này
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Cho phép các phương thức này
                .allowedHeaders("*"); // Cho phép tất cả các tiêu đề
    }
}

