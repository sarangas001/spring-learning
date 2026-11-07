package com.telusko.advice;

import java.time.LocalTime;

public class ErrorDetails {
    private String message;
    private Integer StatusCode;
    private LocalTime timeStamp;

    public ErrorDetails() {
    }

    public ErrorDetails(String message, Integer statusCode, LocalTime timeStamp) {
        this.message = message;
        StatusCode = statusCode;
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "ErrorDetails{" +
                "message='" + message + '\'' +
                ", StatusCode=" + StatusCode +
                ", timeStamp=" + timeStamp +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(Integer statusCode) {
        StatusCode = statusCode;
    }

    public LocalTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
