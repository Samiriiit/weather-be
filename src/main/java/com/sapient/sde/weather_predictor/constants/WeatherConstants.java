package com.sapient.sde.weather_predictor.constants;

public class WeatherConstants {
    public static final String FAILURE_STATUS = "FAILURE";
    public static final String SUCCESS_STATUS = "SUCCESS";

    //  Codes
    public static final String CITY_NOT_FOUND_CODE = "404";
    public static final String SERVICE_UNAVAILABLE_CODE = "503";
    public static final String NO_RESPONSE_CODE = "408";
    public static final String GLOBAL_EXCEPTION_CODE = "500";

    // Messages
    public static final String CITY_NOT_FOUND_MSG = "City not found";
    public static final String SERVICE_UNAVAILABLE_MSG = "External service unavailable";
    public static final String NO_RESPONSE_MSG = "No response received from external service";
    public static final String UNKNOWN_ERROR_MSG = "An unexpected error occurred";

    private WeatherConstants() {
    }
}
