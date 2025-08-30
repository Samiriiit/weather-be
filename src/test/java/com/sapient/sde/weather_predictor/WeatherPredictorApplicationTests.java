package com.sapient.sde.weather_predictor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;

class WeatherPredictorApplicationMainTest {

	@Test
	void testMain() {
		// Optionally mock SpringApplication.run to avoid starting context
		try (var springApp = mockStatic(SpringApplication.class)) {
			WeatherPredictorApplication.main(new String[]{});
			springApp.verify(() -> SpringApplication.run(WeatherPredictorApplication.class, new String[]{}));
		}
	}
}
