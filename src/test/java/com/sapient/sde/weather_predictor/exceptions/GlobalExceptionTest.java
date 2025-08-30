package com.sapient.sde.weather_predictor.exceptions;

import com.sapient.sde.weather_predictor.constants.WeatherConstants;
import com.sapient.sde.weather_predictor.dto.ErrorDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

import javax.naming.ServiceUnavailableException;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionTest {

    private GlobalException globalException;
    private ServletWebRequest mockRequest;

    @BeforeEach
    void setup() {
        globalException = new GlobalException();
        mockRequest = new ServletWebRequest(new MockHttpServletRequest());
    }

    @Test
    void handleGlobalException_ShouldReturnInternalServerError() {
        Exception ex = new Exception("Some error");

        ResponseEntity<ErrorDto> response = globalException.handleGlobalException(ex, mockRequest);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals(WeatherConstants.FAILURE_STATUS, response.getBody().getStatus());
        assertEquals(WeatherConstants.GLOBAL_EXCEPTION_CODE, response.getBody().getStatusCode());
        assertEquals("Some error", response.getBody().getMessage());
    }

    @Test
    void handleCityNotFound_ShouldReturnBadRequest() {
        CityNotAvailableException ex = new CityNotAvailableException("City not found");

        ResponseEntity<ErrorDto> response = globalException.handleCityNotFound(ex, mockRequest);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(WeatherConstants.FAILURE_STATUS, response.getBody().getStatus());
        assertEquals(WeatherConstants.CITY_NOT_FOUND_CODE, response.getBody().getStatusCode());
        assertEquals("City not found", response.getBody().getMessage());
    }

    @Test
    void handleServiceUnavailable_ShouldReturnServiceUnavailable() {
        ServiceUnavailableException ex = new ServiceUnavailableException("Service down");

        ResponseEntity<ErrorDto> response = globalException.handleServiceUnavailable(ex, mockRequest);

        assertEquals(503, response.getStatusCodeValue());
        assertEquals(WeatherConstants.FAILURE_STATUS, response.getBody().getStatus());
        assertEquals(WeatherConstants.SERVICE_UNAVAILABLE_CODE, response.getBody().getStatusCode());
        assertEquals("Service down", response.getBody().getMessage());
    }
}
