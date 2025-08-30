package com.sapient.sde.weather_predictor.exceptions;

public class CityNotAvailableException extends RuntimeException{
    public CityNotAvailableException(String message) {
        super(message);
    }
}
