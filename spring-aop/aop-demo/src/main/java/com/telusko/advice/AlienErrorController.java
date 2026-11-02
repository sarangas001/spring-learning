package com.telusko.advice;

import com.telusko.exception.AlienNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;

@RestControllerAdvice
public class AlienErrorController {
    @ExceptionHandler(AlienNotFoundException.class)
    public ResponseEntity<ErrorDetails> expression(AlienNotFoundException ex)
    {
        ErrorDetails details = new ErrorDetails();
        details.setMessage(ex.getMessage());
        details.setStatusCode(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<ErrorDetails>(details, HttpStatus.BAD_REQUEST);
    }
}
