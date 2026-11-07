package com.telusko.advice;

import com.telusko.exception.ImageProcessingException;
import com.telusko.exception.ProductNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalTime;

@RestControllerAdvice("com.telusko.controller")
public class ProductErrorController {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleProductNotFound(ProductNotFoundException ex)
    {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), 400, LocalTime.now());
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImageProcessingException.class)
    public ResponseEntity<ErrorDetails> handleImage(ImageProcessingException ex)
    {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), 400, LocalTime.now());
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleExceptions(Exception ex)
    {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), 400, LocalTime.now());
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
