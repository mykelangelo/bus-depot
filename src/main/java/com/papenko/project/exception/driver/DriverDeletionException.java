package com.papenko.project.exception.driver;

import com.papenko.project.entity.Driver;

public class DriverDeletionException extends RuntimeException {
    public DriverDeletionException(Driver driver, Exception exception) {
        super("Could not delete driver " + driver, exception);
    }
}
