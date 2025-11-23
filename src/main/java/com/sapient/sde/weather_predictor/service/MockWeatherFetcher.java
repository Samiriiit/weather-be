package com.sapient.sde.weather_predictor.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class MockWeatherFetcher implements WeatherFetcher {
    @Override
    public JsonNode fetchWeather(String city){
        return new ObjectMapper().createObjectNode(); // dummy data
    }
}
