package com.papenko.project.exception.driver;

import com.papenko.project.entity.Driver;

public class DriverAwarenessChangeException extends RuntimeException {
    public DriverAwarenessChangeException(Driver driver, boolean awareness, Exception exception) {
        super("Could not change driver " + driver + " with new awareness " + awareness, exception);
    }
}
