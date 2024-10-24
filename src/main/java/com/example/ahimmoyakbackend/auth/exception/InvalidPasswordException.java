package com.example.ahimmoyakbackend.auth.exception;

public class InvalidPasswordException extends IllegalArgumentException{
    public InvalidPasswordException(String message) {
        super(message);
    }
}
