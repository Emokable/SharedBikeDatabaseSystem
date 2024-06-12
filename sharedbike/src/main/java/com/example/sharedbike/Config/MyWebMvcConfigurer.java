package com.example.sharedbike.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173","localhost:8088", "127.0.0.1:5173","127.0.0.1:8088")
                .allowCredentials(true)
                .allowedMethods("GET","POST","PUT","DELETE","HEAD")
                .exposedHeaders("X-Authorization-With");
    }
}