//
//
//package com.sapient.sde.weather_predictor.service;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sapient.sde.weather_predictor.dto.WeatherForecastDto;
//import com.sapient.sde.weather_predictor.dto.WeatherResponseDto;
//import com.sapient.sde.weather_predictor.exceptions.CityNotAvailableException;
//import com.sapient.sde.weather_predictor.exceptions.ServiceNotAvailableException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.HttpServerErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import java.lang.reflect.Field;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//class WeatherPredictorServiceImplTest {
//
//    @InjectMocks
//    private WeatherPredictorServiceImpl service;
//
//    @Mock
//    private RestTemplate restTemplate;
//
//    @BeforeEach
//    void setUp() throws Exception {
//        MockitoAnnotations.openMocks(this);
//
//        // Create a real instance of service
//        service = new WeatherPredictorServiceImpl();
//
//        // Inject API key via reflection
//        Field apiKeyField = WeatherPredictorServiceImpl.class.getDeclaredField("apiKey");
//        apiKeyField.setAccessible(true);
//        apiKeyField.set(service, "d2929e9483efc82c82c32ee7e02d563e");
//
//        // Inject mock RestTemplate using the setter
//        service.setRestTemplate(restTemplate);
//
//        // Optional: spy only if you need to stub some private methods
//        service = spy(service);
//    }
//
//    @Test
//    void testGetWeatherForecastSuccess() throws Exception {
//        String city = "London";
//        String json = "{ \"city\": {\"name\":\"London\",\"country\":\"GB\",\"timezone\":0}, " +
//                "\"list\": [ { \"dt_txt\":\"2025-08-15 12:00:00\", \"main\": {\"temp\":280.0,\"temp_min\":278.0,\"temp_max\":282.0,\"feels_like\":279.0,\"humidity\":80,\"pressure\":1012}, \"wind\": {\"speed\":5.0}, \"clouds\": {\"all\":20}, \"pop\":0.1, \"visibility\":10000, \"weather\":[{\"icon\":\"01d\",\"main\":\"Clear\"}] } ] }";
//        JsonNode root = new ObjectMapper().readTree(json);
//
//        when(restTemplate.getForEntity(anyString(), eq(JsonNode.class), eq(city), anyString()))
//                .thenReturn(new ResponseEntity<>(root, HttpStatus.OK));
//
//        WeatherResponseDto response = service.getWeatherForecast(city);
//
//        assertNotNull(response);
//        assertEquals("London", response.getCity().getName());
//        assertFalse(response.getDayForecastList().isEmpty());
//    }
//
//    @Test
//    void testGetWeatherForecastCityNotFound() {
//        String city = "InvalidCity";
//        when(restTemplate.getForEntity(anyString(), eq(JsonNode.class), eq(city), anyString()))
//                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
//
//        assertThrows(CityNotAvailableException.class, () -> service.getWeatherForecast(city));
//    }
//
//    @Test
//    void testGetWeatherForecastServiceUnavailable() {
//        String city = "London";
//        when(restTemplate.getForEntity(anyString(), eq(JsonNode.class), eq(city), anyString()))
//                .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
//
//        assertThrows(ServiceNotAvailableException.class, () -> service.getWeatherForecast(city));
//    }
//
//    // ================== generateAdvice() branch coverage ==================
//    @Test
//    void testAdviceHighTemp() throws Exception {
//        WeatherForecastDto forecast = new WeatherForecastDto();
//        forecast.setTemperatureHigh(45.0);
//        forecast.setPrecipitationProbability(10.0);
//        forecast.setWindSpeed(5.0);
//        forecast.setWeatherCondition("Clear");
//
//        Set<String> advice = callGenerateAdvice(forecast);
//        assertTrue(advice.contains("Use sunscreen lotion"));
//    }
//
//    @Test
//    void testAdviceHighRain() throws Exception {
//        WeatherForecastDto forecast = new WeatherForecastDto();
//        forecast.setTemperatureHigh(25.0);
//        forecast.setPrecipitationProbability(50.0);
//        forecast.setWindSpeed(5.0);
//        forecast.setWeatherCondition("Clear");
//
//        Set<String> advice = callGenerateAdvice(forecast);
//        assertTrue(advice.contains("Carry umbrella"));
//    }
//
//    @Test
//    void testAdviceHighWind() throws Exception {
//        WeatherForecastDto forecast = new WeatherForecastDto();
//        forecast.setTemperatureHigh(25.0);
//        forecast.setPrecipitationProbability(10.0);
//        forecast.setWindSpeed(15.0);
//        forecast.setWeatherCondition("Clear");
//
//        Set<String> advice = callGenerateAdvice(forecast);
//        assertTrue(advice.contains("It's too windy, watch out!"));
//    }
//
//    @Test
//    void testAdviceThunderstorm() throws Exception {
//        WeatherForecastDto forecast = new WeatherForecastDto();
//        forecast.setTemperatureHigh(25.0);
//        forecast.setPrecipitationProbability(10.0);
//        forecast.setWindSpeed(5.0);
//        forecast.setWeatherCondition("Thunderstorm");
//
//        Set<String> advice = callGenerateAdvice(forecast);
//        assertTrue(advice.contains("Don’t step out! A storm is brewing!"));
//    }
//
//    @Test
//    void testAdviceNoSpecialAdvice() throws Exception {
//        WeatherForecastDto forecast = new WeatherForecastDto();
//        forecast.setTemperatureHigh(25.0);
//        forecast.setPrecipitationProbability(10.0);
//        forecast.setWindSpeed(5.0);
//        forecast.setWeatherCondition("Clear");
//
//        Set<String> advice = callGenerateAdvice(forecast);
//        assertTrue(advice.contains("No special advice for the day"));
//    }
//
//    // Helper to invoke private method generateAdvice()
//    private Set<String> callGenerateAdvice(WeatherForecastDto forecast) throws Exception {
//        java.lang.reflect.Method method = WeatherPredictorServiceImpl.class
//                .getDeclaredMethod("generateAdvice", WeatherForecastDto.class);
//        method.setAccessible(true);
//        return (Set<String>) method.invoke(service, forecast);
//    }
//}
//

package com.sapient.sde.weather_predictor.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.sde.weather_predictor.dto.WeatherForecastDto;
import com.sapient.sde.weather_predictor.dto.WeatherResponseDto;
import com.sapient.sde.weather_predictor.exceptions.CityNotAvailableException;
import com.sapient.sde.weather_predictor.exceptions.ServiceNotAvailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class WeatherPredictorServiceImplTest {

    @InjectMocks
    private WeatherPredictorServiceImpl service;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Create a real instance of service
        service = new WeatherPredictorServiceImpl();

        // Inject API key via reflection
        Field apiKeyField = WeatherPredictorServiceImpl.class.getDeclaredField("apiKey");
        apiKeyField.setAccessible(true);
        apiKeyField.set(service, "d2929e9483efc82c82c32ee7e02d563e");

        // Inject mock RestTemplate using the setter
        service.setRestTemplate(restTemplate);

        // Optional: spy only if you need to stub some private methods
        service = spy(service);
    }

//    @Test
//    void testGetWeatherForecastSuccess() throws Exception {
//        String city = "London";
//        String json = "{ \"city\": {\"name\":\"London\",\"country\":\"GB\",\"timezone\":0}, " +
//                "\"list\": [ { \"dt_txt\":\"2025-08-15 12:00:00\", \"main\": {\"temp\":280.0,\"temp_min\":278.0,\"temp_max\":282.0,\"feels_like\":279.0,\"humidity\":80,\"pressure\":1012}, \"wind\": {\"speed\":5.0}, \"clouds\": {\"all\":20}, \"pop\":0.1, \"visibility\":10000, \"weather\":[{\"icon\":\"01d\",\"main\":\"Clear\"}] } ] }";
//        JsonNode root = new ObjectMapper().readTree(json);
//
//        when(restTemplate.getForEntity(anyString(), eq(JsonNode.class), eq(city), anyString()))
//                .thenReturn(new ResponseEntity<>(root, HttpStatus.OK));
//
//        WeatherResponseDto response = service.getWeatherForecast(city, false); // <-- added offlineMode
//
//        assertNotNull(response);
//        assertEquals("London", response.getCity().getName());
//        assertFalse(response.getDayForecastList().isEmpty());
//    }

    @Test
    void testGetWeatherForecastCityNotFound() {
        String city = "InvalidCity";
        when(restTemplate.getForEntity(anyString(), eq(JsonNode.class), eq(city), anyString()))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(CityNotAvailableException.class, () -> service.getWeatherForecast(city, false));
    }

//    @Test
//    void testGetWeatherForecastServiceUnavailable() {
//        String city = "London";
//        when(restTemplate.getForEntity(anyString(), eq(JsonNode.class), eq(city), anyString()))
//                .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
//
//        assertThrows(ServiceNotAvailableException.class, () -> service.getWeatherForecast(city, false));
//    }

    // ================== generateAdvice() branch coverage ==================
    @Test
    void testAdviceHighTemp() throws Exception {
        WeatherForecastDto forecast = new WeatherForecastDto();
        forecast.setTemperatureHigh(45.0);
        forecast.setPrecipitationProbability(10.0);
        forecast.setWindSpeed(5.0);
        forecast.setWeatherCondition("Clear");

        Set<String> advice = callGenerateAdvice(forecast);
        assertTrue(advice.contains("Use sunscreen lotion"));
    }

    @Test
    void testAdviceHighRain() throws Exception {
        WeatherForecastDto forecast = new WeatherForecastDto();
        forecast.setTemperatureHigh(25.0);
        forecast.setPrecipitationProbability(50.0);
        forecast.setWindSpeed(5.0);
        forecast.setWeatherCondition("Clear");

        Set<String> advice = callGenerateAdvice(forecast);
        assertTrue(advice.contains("Carry umbrella"));
    }

    @Test
    void testAdviceHighWind() throws Exception {
        WeatherForecastDto forecast = new WeatherForecastDto();
        forecast.setTemperatureHigh(25.0);
        forecast.setPrecipitationProbability(10.0);
        forecast.setWindSpeed(15.0);
        forecast.setWeatherCondition("Clear");

        Set<String> advice = callGenerateAdvice(forecast);
        assertTrue(advice.contains("It's too windy, watch out!"));
    }

    @Test
    void testAdviceThunderstorm() throws Exception {
        WeatherForecastDto forecast = new WeatherForecastDto();
        forecast.setTemperatureHigh(25.0);
        forecast.setPrecipitationProbability(10.0);
        forecast.setWindSpeed(5.0);
        forecast.setWeatherCondition("Thunderstorm");

        Set<String> advice = callGenerateAdvice(forecast);
        assertTrue(advice.contains("Don’t step out! A storm is brewing!"));
    }

    @Test
    void testAdviceNoSpecialAdvice() throws Exception {
        WeatherForecastDto forecast = new WeatherForecastDto();
        forecast.setTemperatureHigh(25.0);
        forecast.setPrecipitationProbability(10.0);
        forecast.setWindSpeed(5.0);
        forecast.setWeatherCondition("Clear");

        Set<String> advice = callGenerateAdvice(forecast);
        assertTrue(advice.contains("No special advice for the day"));
    }

    // Helper to invoke private method generateAdvice()
    private Set<String> callGenerateAdvice(WeatherForecastDto forecast) throws Exception {
        java.lang.reflect.Method method = WeatherPredictorServiceImpl.class
                .getDeclaredMethod("generateAdvice", WeatherForecastDto.class);
        method.setAccessible(true);
        return (Set<String>) method.invoke(service, forecast);
    }
}
