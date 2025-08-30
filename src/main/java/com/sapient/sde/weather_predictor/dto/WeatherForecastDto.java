package com.sapient.sde.weather_predictor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherForecastDto {
    private String date;
    private Double temperatureHigh;
    private Double temperatureLow;
    private Double temperatureCurrent;
    private Double temperatureFeelsLike;
    private Double precipitationProbability;
    private Double visibility;
    private Double windSpeed;
    private Double cloudiness;
    private Integer humidity;
    private Integer pressure;
    private Double rainTotal;
    private String icon;
    private String weatherCondition;

    public Set<String> getAdvice() {
        return advice;
    }

    public void setAdvice(Set<String> advice) {
        this.advice = advice;
    }

    private Set<String> advice;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTemperatureHigh() {
        return temperatureHigh;
    }

    public void setTemperatureHigh(Double temperatureHigh) {
        this.temperatureHigh = temperatureHigh;
    }

    public Double getTemperatureLow() {
        return temperatureLow;
    }

    public void setTemperatureLow(Double temperatureLow) {
        this.temperatureLow = temperatureLow;
    }

    public Double getTemperatureCurrent() {
        return temperatureCurrent;
    }

    public void setTemperatureCurrent(Double temperatureCurrent) {
        this.temperatureCurrent = temperatureCurrent;
    }

    public Double getTemperatureFeelsLike() {
        return temperatureFeelsLike;
    }

    public void setTemperatureFeelsLike(Double temperatureFeelsLike) {
        this.temperatureFeelsLike = temperatureFeelsLike;
    }

    public Double getPrecipitationProbability() {
        return precipitationProbability;
    }

    public void setPrecipitationProbability(Double precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
    }

    public Double getVisibility() {
        return visibility;
    }

    public void setVisibility(Double visibility) {
        this.visibility = visibility;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(Double cloudiness) {
        this.cloudiness = cloudiness;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Double getRainTotal() {
        return rainTotal;
    }

    public void setRainTotal(Double rainTotal) {
        this.rainTotal = rainTotal;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }
}
