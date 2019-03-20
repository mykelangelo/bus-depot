package com.papenko.project.exception.driver;

import com.papenko.project.entity.Driver;

import java.util.List;

public class DriversSearchException extends RuntimeException {
    public DriversSearchException(List<Driver> drivers, Exception exception) {
        super("Could not find all drivers. Current drivers found: " + drivers, exception);
    }
}
