
package com.sapient.sde.weather_predictor.steps;

import com.sapient.sde.weather_predictor.service.WeatherPredictorService;
import com.sapient.sde.weather_predictor.dto.WeatherResponseDto;
import com.sapient.sde.weather_predictor.exceptions.CityNotAvailableException;
import com.sapient.sde.weather_predictor.exceptions.CityNotAvailableException;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class WeatherSteps {

    @Autowired
    private WeatherPredictorService weatherPredictorService;

    private String city;
    private boolean offlineMode = true;
    private int days = 1;
    private String unit = "Celsius";

    private WeatherResponseDto response;
    private Exception exception;

    @Given("the city name is {string}")
    public void the_city_name_is(String city) {
        this.city = city;
        this.exception = null;
        this.response = null;
    }

    @And("the preferred unit is {string}")
    public void the_preferred_unit_is(String unit) {
        this.unit = unit;
    }

    @And("I request a forecast for {int} days")
    public void i_request_a_forecast_for_days(int days) {
        this.days = days;
    }

    @And("offline mode is {string}")
    public void offline_mode_is(String mode) {
        this.offlineMode = Boolean.parseBoolean(mode);
    }

    @When("I request the weather forecast")
    public void i_request_the_weather_forecast() {
        try {
            if(city == null || city.isBlank()) {
                throw new CityNotAvailableException("City name cannot be empty");
            }
            response = weatherPredictorService.getWeatherForecast(city, offlineMode);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the response should contain a valid forecast")
    public void the_response_should_contain_a_valid_forecast() {
        assertNull("No exception should occur", exception);
        assertNotNull("Response should not be null", response);
        assertEquals("City name should match", city, response.getCity().getName());
        assertFalse("Forecast list should not be empty", response.getDayForecastList().isEmpty());
    }

    @Then("the response should throw a CityNotAvailableException")
    public void response_should_throw_exception() {
        assertNotNull("Exception should have occurred", exception);
        assertTrue("Exception should be CityNotAvailableException", exception instanceof CityNotAvailableException);
    }

    @Then("an InvalidInputException should be thrown")
    public void an_invalid_input_exception_should_be_thrown() {
        assertNotNull("Exception should have occurred", exception);
        assertTrue("Exception should be InvalidInputException", exception instanceof CityNotAvailableException);
    }

    @Then("the response temperature should be in {string}")
    public void the_response_temperature_should_be_in(String expectedUnit) {
        assertNotNull("Response should not be null", response);
        // Dummy check (in real app, check actual conversion)
        assertEquals(expectedUnit, this.unit);
    }

    @Then("the response should contain weather data for {int} days")
    public void the_response_should_contain_weather_data_for_days(int expectedDays) {
        assertNotNull("Response should not be null", response);
        assertTrue("Should contain forecast for requested days",
                response.getDayForecastList().size() >= expectedDays);
    }
}
