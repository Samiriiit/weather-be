package com.sapient.sde.weather_predictor.service;

import com.sapient.sde.weather_predictor.dto.WeatherResponseDto;

public interface WeatherCache {
    void save(String city, WeatherResponseDto dto);
    WeatherResponseDto get(String city);
}
