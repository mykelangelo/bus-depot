package com.papenko.project.exception.user;

import com.papenko.project.entity.User;

public class UserDeletionException extends RuntimeException {
    public UserDeletionException(User user, Exception exception) {
        super("Could not delete user " + user, exception);
    }
}
