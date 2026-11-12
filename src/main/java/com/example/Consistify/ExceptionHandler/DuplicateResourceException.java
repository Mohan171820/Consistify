package com.example.Consistify.ExceptionHandler;

// This exception is thrown when an attempt is made to create a resource that already exists in the system.
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}

