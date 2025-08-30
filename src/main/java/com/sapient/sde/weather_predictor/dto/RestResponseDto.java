package com.sapient.sde.weather_predictor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class RestResponseDto {
    private String status;
    private String statusCode;
    private WeatherResponseDto weatherResponse;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public WeatherResponseDto getWeatherResponse() {
        return weatherResponse;
    }

    public void setWeatherResponse(WeatherResponseDto weatherResponse) {
        this.weatherResponse = weatherResponse;
    }

    public RestResponseDto(String status, String statusCode, WeatherResponseDto weatherResponse) {
        this.status = status;
        this.statusCode = statusCode;
        this.weatherResponse = weatherResponse;
    }
}
