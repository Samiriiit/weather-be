//package com.sapient.sde.weather_predictor.controller;
//
//import com.sapient.sde.weather_predictor.controllers.WeatherPredictorController;
//import com.sapient.sde.weather_predictor.dto.RestResponseDto;
//import com.sapient.sde.weather_predictor.dto.WeatherResponseDto;
//import com.sapient.sde.weather_predictor.service.WeatherPredictorService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class WeatherPredictorControllerTest {
//
//    @Mock
//    private WeatherPredictorService weatherPredictorService;
//
//    @InjectMocks
//    private WeatherPredictorController weatherPredictorController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testWeatherPredictor_withValidCity_returnsOk() {
//        String city = "London";
//        WeatherResponseDto mockResponse = new WeatherResponseDto();
//        // Optionally, set mockResponse fields if needed
//        when(weatherPredictorService.getWeatherForecast(city)).thenReturn(mockResponse);
//
//        RestResponseDto response = weatherPredictorController.weatherPredictor(city);
//
//        assertEquals("OK", response.getStatus());
//        assertEquals("200", response.getStatusCode());
//        assertEquals(mockResponse, response.getWeatherResponse());
//        verify(weatherPredictorService, times(1)).getWeatherForecast(city);
//    }
//
//    @Test
//    void testWeatherPredictor_withBlankCity_returnsError() {
//        String city = "  ";
//
//        RestResponseDto response = weatherPredictorController.weatherPredictor(city);
//
//        assertEquals("ERROR", response.getStatus());
//        assertEquals("400", response.getStatusCode());
//        // The data should be an empty WeatherResponseDto
//        assertEquals(WeatherResponseDto.class, response.getWeatherResponse().getClass());
//        verify(weatherPredictorService, never()).getWeatherForecast(anyString());
//    }
//
//    @Test
//    void testWeatherPredictor_withNullCity_returnsError() {
//        RestResponseDto response = weatherPredictorController.weatherPredictor(null);
//
//        assertEquals("ERROR", response.getStatus());
//        assertEquals("400", response.getStatusCode());
//        assertEquals(WeatherResponseDto.class, response.getWeatherResponse().getClass());
//        verify(weatherPredictorService, never()).getWeatherForecast(anyString());
//    }
//}
