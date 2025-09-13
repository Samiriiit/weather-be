package com.sapient.sde.weather_predictor;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = WeatherPredictorApplication.class)
public class CucumberSpringConfiguration {

}
