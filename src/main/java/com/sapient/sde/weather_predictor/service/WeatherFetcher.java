package com.sapient.sde.weather_predictor.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface WeatherFetcher {
    /**
     * -------------------------------------------------------
     * STRATEGY PATTERN (Interface)
     *
     * WeatherFetcher defines the *strategy* (algorithm) for
     * fetching weather data. Different implementations like
     * OpenWeatherFetcher and MockWeatherFetcher provide
     * interchangeable behaviors.
     * -------------------------------------------------------
     */
    JsonNode fetchWeather(String city);
}
