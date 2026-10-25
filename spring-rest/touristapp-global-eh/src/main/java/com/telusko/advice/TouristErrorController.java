package com.telusko.advice;

import com.telusko.exception.TouristNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class TouristErrorController {
    @ExceptionHandler(TouristNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleTouristNotFoundException(TouristNotFoundException ex)
    {
        ErrorDetails details = new ErrorDetails();
        details.setMessage(ex.getMessage());
        details.setStatusCode(404);
        details.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ErrorDetails>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleTouristException(Exception ex)
    {
        ErrorDetails details = new ErrorDetails();
        details.setMessage("Some problem");
        details.setStatusCode(404);
        details.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ErrorDetails>(details, HttpStatus.BAD_REQUEST);
    }

}
