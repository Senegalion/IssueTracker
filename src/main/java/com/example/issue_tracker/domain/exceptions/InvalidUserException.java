package com.example.issue_tracker.domain.exceptions;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String message) {
        super(message);
    }
}
