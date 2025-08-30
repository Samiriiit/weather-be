package com.sapient.sde.weather_predictor.dto;


import java.util.List;

public class WeatherResponseDto {
    private List<DayForecastDto> dayForecastList;

    private CityDto city;

    public List<DayForecastDto> getDayForecastList() {
        return dayForecastList;
    }

    public void setDayForecastList(List<DayForecastDto> dayForecastList) {
        this.dayForecastList = dayForecastList;
    }

    public CityDto getCity() {
        return city;
    }

    public void setCity(CityDto city) {
        this.city = city;
    }
}
