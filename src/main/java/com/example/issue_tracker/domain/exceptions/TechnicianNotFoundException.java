package com.example.issue_tracker.domain.exceptions;

public class TechnicianNotFoundException extends RuntimeException {
    public TechnicianNotFoundException(String message) {
        super(message);
    }
}
