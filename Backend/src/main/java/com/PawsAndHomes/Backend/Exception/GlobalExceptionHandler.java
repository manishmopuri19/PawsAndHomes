package com.PawsAndHomes.Backend.Exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("error", "Resource Not Found");
        error.put("message", ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
   @ExceptionHandler(Exception.class)
public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {

    ex.printStackTrace(); // 👈 READ THIS IN CONSOLE

    Map<String, Object> error = new HashMap<>();
    error.put("timestamp", LocalDateTime.now());
    error.put("status", 500);
    error.put("exception", ex.getClass().getName());
    error.put("message", ex.getMessage());

    return ResponseEntity.status(500).body(error);
}
}