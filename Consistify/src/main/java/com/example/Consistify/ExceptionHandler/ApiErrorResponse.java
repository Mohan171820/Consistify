package com.example.Consistify.ExceptionHandler;

import java.time.LocalDateTime;

// This class represents the structure of the error response that will be sent to the client when an exception occurs.
public class ApiErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;

    // Getters and setters for the fields

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public ApiErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}