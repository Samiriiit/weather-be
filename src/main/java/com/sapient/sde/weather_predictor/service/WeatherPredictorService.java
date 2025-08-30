package com.sapient.sde.weather_predictor.service;

import com.sapient.sde.weather_predictor.dto.WeatherResponseDto;

public interface WeatherPredictorService {
   public WeatherResponseDto getWeatherForecast(String city, boolean offlineMode);
}
