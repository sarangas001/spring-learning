package com.telusko.advice;

import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

public class ErrorDetails {
    private String message;
    private HttpStatus statusCode;
    private Timestamp timestamp;

    public ErrorDetails() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
