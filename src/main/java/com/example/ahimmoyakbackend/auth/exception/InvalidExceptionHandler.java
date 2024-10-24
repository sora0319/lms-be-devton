package com.example.ahimmoyakbackend.auth.exception;

import com.example.ahimmoyakbackend.auth.dto.UserVerificationResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidExceptionHandler {
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<UserVerificationResponseDTO> handleInvalidPasswordException(InvalidPasswordException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new UserVerificationResponseDTO(e.getMessage()));
    }

}
