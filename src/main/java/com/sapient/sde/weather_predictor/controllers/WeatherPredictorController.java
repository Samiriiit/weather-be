package com.sapient.sde.weather_predictor.controllers;

import com.sapient.sde.weather_predictor.dto.RestResponseDto;
import com.sapient.sde.weather_predictor.dto.WeatherResponseDto;
import com.sapient.sde.weather_predictor.service.WeatherPredictorService;
import com.sapient.sde.weather_predictor.validator.CityValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController 
  /** --------------------------------
         *  Singleton pattern
         * Only one instance handles all incoming requests
         * --------------------------------
         */
@Tag(name = "Weather API", description = "Get weather data from cache or external API")
public class WeatherPredictorController {
    @Autowired
    private final WeatherPredictorService weatherPredictorService;

    public WeatherPredictorController(WeatherPredictorService weatherPredictorService) {
        /**
         * --------------------------------
         * DIP: Dependency Inversion Principle
         * Controller depends on abstraction (WeatherPredictorService interface),
         * not on any concrete implementation.
         * Constructor injection ensures loose coupling.
         * --------------------------------
         */
        this.weatherPredictorService = weatherPredictorService;
    }

    @GetMapping (value = "weather-prediction") // v1/weather-prediction
    @Operation(
            summary = "Get Weather Prediction",
            description = "Fetch weather prediction for a given city from cache or external API"
    )
    public RestResponseDto weatherPredictor( @Parameter(description = "Name of the city", example = "London")
                                                 @RequestParam String city,
                                             @Parameter(description = "Enable offline mode (use Redis cache)", example = "false")
                                             @RequestParam(defaultValue = "false") boolean offlineMode // param= version=1
                                             ){
        /**
         * --------------------------------
         * SRP: Single Responsibility Principle
         * This controller only handles HTTP request/response.
         * Validation is delegated to CityValidator → one responsibility per class.
         * --------------------------------
         */
        if (!CityValidator.isValid(city)) {
            return new RestResponseDto("ERROR", "400", new WeatherResponseDto());
        }
        WeatherResponseDto weatherResponse = weatherPredictorService.getWeatherForecast(city, offlineMode);
        return new RestResponseDto("OK","200",weatherPredictorService.getWeatherForecast(city, offlineMode));

    }

}
