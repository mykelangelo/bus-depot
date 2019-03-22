package com.papenko.project.exception.driver;

public class DriverCreationException extends RuntimeException {
    public DriverCreationException(String driverEmail, Exception exception) {
        super("Could not create driver " + driverEmail, exception);
    }
}
