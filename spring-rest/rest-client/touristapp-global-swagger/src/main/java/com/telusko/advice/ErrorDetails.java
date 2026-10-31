package com.telusko.advice;

import java.time.LocalDateTime;

public class ErrorDetails
{
    private String message;
    private Integer statusCode;
    private LocalDateTime timestamp;

    public ErrorDetails() {
    }

    public ErrorDetails(String message, Integer statusCode, LocalDateTime timestamp) {
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ErrorDetails{" +
                "message='" + message + '\'' +
                ", statusCode=" + statusCode +
                ", timestamp=" + timestamp +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
