package com.sapient.sde.weather_predictor.controllers;

import com.sapient.sde.weather_predictor.dto.RestResponseDto;
import com.sapient.sde.weather_predictor.dto.WeatherResponseDto;
import com.sapient.sde.weather_predictor.service.WeatherPredictorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Weather API", description = "Get weather data from cache or external API")
public class WeatherPredictorController {
    @Autowired
    private WeatherPredictorService weatherPredictorService;

    @GetMapping (value = "weather-prediction")
    @Operation(
            summary = "Get Weather Prediction",
            description = "Fetch weather prediction for a given city from cache or external API"
    )
    public RestResponseDto weatherPredictor( @Parameter(description = "Name of the city", example = "London")
                                                 @RequestParam String city,
                                             @Parameter(description = "Enable offline mode (use Redis cache)", example = "false")
                                             @RequestParam(defaultValue = "false") boolean offlineMode
                                             ){
        if (city == null || city.isBlank()) {
            return new RestResponseDto("ERROR", "400", new WeatherResponseDto());
        }
        WeatherResponseDto weatherResponse = weatherPredictorService.getWeatherForecast(city, offlineMode);
        return new RestResponseDto("OK","200",weatherResponse);

    }

}
