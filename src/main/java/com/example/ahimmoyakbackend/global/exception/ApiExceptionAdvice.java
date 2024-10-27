package com.example.ahimmoyakbackend.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdvice {
    @ExceptionHandler({ApiException.class})
    public ResponseEntity<String> exceptionHandler(HttpServletRequest req, ApiException e) {
        // e.printStackTrace();
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(e.getMessage());
    }
}
