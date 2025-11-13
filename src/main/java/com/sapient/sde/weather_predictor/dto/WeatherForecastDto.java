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

// Builder pattern allows creating WeatherForecastDto objects step-by-step.simplify process for big projects
//	Object creation step-by-step customizion without directly calling constructor with lots of parameters.



// // Useful for objects with many optional fields (like here).
// @JsonInclude(JsonInclude.Include.NON_NULL)
// public class WeatherForecastDto {

//     private String date;
//     private Double temperatureHigh;
//     private Double temperatureLow;
//     private Double temperatureCurrent;
//     private Double temperatureFeelsLike;
//     private Double precipitationProbability;
//     private Double visibility;
//     private Double windSpeed;
//     private Double cloudiness;
//     private Integer humidity;
//     private Integer pressure;
//     private Double rainTotal;
//     private String icon;
//     private String weatherCondition;
//     private Set<String> advice;

//     private WeatherForecastDto(Builder builder) {
//         this.date = builder.date;
//         this.temperatureHigh = builder.temperatureHigh;
//         this.temperatureLow = builder.temperatureLow;
//         this.temperatureCurrent = builder.temperatureCurrent;
//         this.temperatureFeelsLike = builder.temperatureFeelsLike;
//         this.precipitationProbability = builder.precipitationProbability;
//         this.visibility = builder.visibility;
//         this.windSpeed = builder.windSpeed;
//         this.cloudiness = builder.cloudiness;
//         this.humidity = builder.humidity;
//         this.pressure = builder.pressure;
//         this.rainTotal = builder.rainTotal;
//         this.icon = builder.icon;
//         this.weatherCondition = builder.weatherCondition;
//         this.advice = builder.advice;
//     }

//     // Builder class
//     public static class Builder {
//         private String date;
//         private Double temperatureHigh;
//         private Double temperatureLow;
//         private Double temperatureCurrent;
//         private Double temperatureFeelsLike;
//         private Double precipitationProbability;
//         private Double visibility;
//         private Double windSpeed;
//         private Double cloudiness;
//         private Integer humidity;
//         private Integer pressure;
//         private Double rainTotal;
//         private String icon;
//         private String weatherCondition;
//         private Set<String> advice;

//         public Builder setDate(String date) {
//             this.date = date;
//             return this;
//         }

//         public Builder setTemperatureHigh(Double temperatureHigh) {
//             this.temperatureHigh = temperatureHigh;
//             return this;
//         }

//         public Builder setTemperatureLow(Double temperatureLow) {
//             this.temperatureLow = temperatureLow;
//             return this;
//         }

//         public Builder setTemperatureCurrent(Double temperatureCurrent) {
//             this.temperatureCurrent = temperatureCurrent;
//             return this;
//         }

//         public Builder setTemperatureFeelsLike(Double temperatureFeelsLike) {
//             this.temperatureFeelsLike = temperatureFeelsLike;
//             return this;
//         }

//         public Builder setPrecipitationProbability(Double precipitationProbability) {
//             this.precipitationProbability = precipitationProbability;
//             return this;
//         }

//         public Builder setVisibility(Double visibility) {
//             this.visibility = visibility;
//             return this;
//         }

//         public Builder setWindSpeed(Double windSpeed) {
//             this.windSpeed = windSpeed;
//             return this;
//         }

//         public Builder setCloudiness(Double cloudiness) {
//             this.cloudiness = cloudiness;
//             return this;
//         }

//         public Builder setHumidity(Integer humidity) {
//             this.humidity = humidity;
//             return this;
//         }

//         public Builder setPressure(Integer pressure) {
//             this.pressure = pressure;
//             return this;
//         }

//         public Builder setRainTotal(Double rainTotal) {
//             this.rainTotal = rainTotal;
//             return this;
//         }

//         public Builder setIcon(String icon) {
//             this.icon = icon;
//             return this;
//         }

//         public Builder setWeatherCondition(String weatherCondition) {
//             this.weatherCondition = weatherCondition;
//             return this;
//         }

//         public Builder setAdvice(Set<String> advice) {
//             this.advice = advice;
//             return this;
//         }

//         public WeatherForecastDto build() {
//             return new WeatherForecastDto(this);
//         }
//     }

//     // Example: How to create a WeatherForecastDto using the Builder
//     public static void main(String[] args) {
//         WeatherForecastDto forecast = new WeatherForecastDto.Builder()
//                 .setDate("2025-10-05")
//                 .setTemperatureHigh(35.5)
//                 .setTemperatureLow(22.0)
//                 .setHumidity(70)
//                 .setAdvice(Set.of("Carry umbrella", "Wear sunscreen"))
//                 .build();

//         System.out.println("Forecast Date: " + forecast.date);
//     }
// }