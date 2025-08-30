package com.sapient.sde.weather_predictor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // Make allowed origins configurable via application.properties
    @Value("${app.cors.allowed-origins}")
    private String[] allowedOrigins;

    private static final String[] ALLOWED_METHODS = { "GET" };
    private static final String[] ALLOWED_HEADERS = { "*" };

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Apply CORS to weather-related endpoints
        registry.addMapping("/weather-prediction/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods(ALLOWED_METHODS)
                .allowedHeaders(ALLOWED_HEADERS)
                .allowCredentials(true);

        registry.addMapping("/weather/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods(ALLOWED_METHODS)
                .allowedHeaders(ALLOWED_HEADERS)
                .allowCredentials(true);
    }
}
