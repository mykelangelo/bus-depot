package com.papenko.project.exception;

public class UserSearchException extends RuntimeException {
    public UserSearchException(String email, Exception exception) {
        super("Could not find user by email " + email, exception);
    }
}
