package com.telusko.exception;

public class AlienNotFoundException extends RuntimeException {
    public AlienNotFoundException(String message) {
        super(message);
    }
}
