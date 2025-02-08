package com.example.demo.exception;

public class ActivityNotFoundException extends RuntimeException {
    public ActivityNotFoundException(String message) {
        super(message);
    }
}
