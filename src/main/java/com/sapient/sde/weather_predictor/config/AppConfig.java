package com.sapient.sde.weather_predictor.config;

import com.sapient.sde.weather_predictor.service.OpenWeatherFetcher;
import com.sapient.sde.weather_predictor.service.WeatherFetcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// src/main/java/com/sapient/sde/weather_predictor/config/AppConfig.java
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

