package com.papenko.project.exception.driver;

import com.papenko.project.entity.Bus;

public class DriverSearchException extends RuntimeException {
    public DriverSearchException(String driverEmail, Exception exception) {
        super("Could not find driver by email " + driverEmail, exception);
    }

    public DriverSearchException(Bus bus, Exception exception) {
        super("Could not find driver by bus " + bus, exception);
    }
}
