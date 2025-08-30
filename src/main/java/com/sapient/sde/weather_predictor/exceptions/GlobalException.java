package com.sapient.sde.weather_predictor.exceptions;

import com.sapient.sde.weather_predictor.constants.WeatherConstants;
import com.sapient.sde.weather_predictor.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.naming.ServiceUnavailableException;

@ControllerAdvice
public class GlobalException {

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorDto> handleGlobalException(Exception ex, WebRequest request) {
            ErrorDto error = new ErrorDto(
                    request.getDescription(false),
                    WeatherConstants.FAILURE_STATUS,
                    WeatherConstants.GLOBAL_EXCEPTION_CODE,
                    ex.getMessage()
            );
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(CityNotAvailableException.class)
        public ResponseEntity<ErrorDto> handleCityNotFound(CityNotAvailableException ex, WebRequest request) {
            ErrorDto error = new ErrorDto(
                    request.getDescription(false),
                    WeatherConstants.FAILURE_STATUS,
                    WeatherConstants.CITY_NOT_FOUND_CODE,
                    ex.getMessage()
            );
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(ServiceUnavailableException.class)
        public ResponseEntity<ErrorDto> handleServiceUnavailable(ServiceUnavailableException ex, WebRequest request) {
            ErrorDto error = new ErrorDto(
                    request.getDescription(false),
                    WeatherConstants.FAILURE_STATUS,
                    WeatherConstants.SERVICE_UNAVAILABLE_CODE,
                    ex.getMessage()
            );
            return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
        }

}
