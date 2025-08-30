package com.sapient.sde.weather_predictor.exceptions;

public class ServiceNotAvailableException extends RuntimeException{
    public ServiceNotAvailableException(String message) {
        super(message);
    }
}
