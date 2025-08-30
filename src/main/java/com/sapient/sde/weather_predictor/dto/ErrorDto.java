package com.sapient.sde.weather_predictor.dto;

public class ErrorDto {
    private String path;
    private String status;
    private String statusCode;
    private String message;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorDto(String path, String status, String statusCode, String message) {
        this.path = path;
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
    }
}
