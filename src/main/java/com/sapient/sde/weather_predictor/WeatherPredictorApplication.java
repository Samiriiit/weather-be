package com.sapient.sde.weather_predictor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.sapient.sde.weather_predictor")
public class WeatherPredictorApplication {

	public static void main(String[] args) {
		System.out.println("Samir");
		SpringApplication.run(WeatherPredictorApplication.class, args);

	}

}