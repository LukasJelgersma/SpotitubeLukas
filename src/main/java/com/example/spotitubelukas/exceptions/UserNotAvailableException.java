package com.example.spotitubelukas.exceptions;

public class UserNotAvailableException extends NullPointerException{
    public UserNotAvailableException() {
        super("User not available");
    }
}
