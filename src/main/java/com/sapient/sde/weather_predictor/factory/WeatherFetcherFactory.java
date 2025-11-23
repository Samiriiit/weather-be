package com.sapient.sde.weather_predictor.factory;

import com.sapient.sde.weather_predictor.service.MockWeatherFetcher;
import com.sapient.sde.weather_predictor.service.OpenWeatherFetcher;
import com.sapient.sde.weather_predictor.service.WeatherFetcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeatherFetcherFactory {

    private final OpenWeatherFetcher openWeatherFetcher;
    private final MockWeatherFetcher mockWeatherFetcher;

    @Value("${app.fetcher.type:openweather}")
    private String fetcherType;

    public WeatherFetcherFactory(OpenWeatherFetcher openWeatherFetcher,
                                 MockWeatherFetcher mockWeatherFetcher) {
        this.openWeatherFetcher = openWeatherFetcher;
        this.mockWeatherFetcher = mockWeatherFetcher;
    }

    public WeatherFetcher getFetcher() {
        // Factory Design Pattern:
        // Decide which fetcher implementation to return depending on provider string.
        // This keeps the decision logic OUT of controllers/services.
        return switch (fetcherType.toLowerCase()) {
            case "mock" -> mockWeatherFetcher;
            default -> openWeatherFetcher;
        };
    }
}
