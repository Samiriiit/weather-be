package com.sapient.sde.weather_predictor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;
import java.util.Set;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DayForecastDto {
//    private String date;
    private List<WeatherForecastDto> weatherForecastList;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, WeatherForecastDto> forecastByPeriod;

    private Set<String> advice;

//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }

    public List<WeatherForecastDto> getWeatherForecastList() {
        return weatherForecastList;
    }

    public void setWeatherForecastList(List<WeatherForecastDto> weatherForecastList) {
        this.weatherForecastList = weatherForecastList;
    }

    public Map<String, WeatherForecastDto> getForecastByPeriod() {
        return forecastByPeriod;
    }

    public void setForecastByPeriod(Map<String, WeatherForecastDto> forecastByPeriod) {
        this.forecastByPeriod = forecastByPeriod;
    }

    public Set<String> getAdvice() {
        return advice;
    }

    public void setAdvice(Set<String> advice) {
        this.advice = advice;
    }
}
