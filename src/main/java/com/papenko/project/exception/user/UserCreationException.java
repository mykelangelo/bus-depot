package com.papenko.project.exception.user;

public class UserCreationException extends RuntimeException {
    public UserCreationException(String userEmail, Exception exception) {
        super("Could not create user with email " + userEmail, exception);
    }
}
