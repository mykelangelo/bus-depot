package com.papenko.project.exception.user;

public class UserSearchException extends RuntimeException {
    public UserSearchException(String email, Exception exception) {
        super("Could not find user by email " + email, exception);
    }
}
