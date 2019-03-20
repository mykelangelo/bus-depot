package com.papenko.project.exception.driver;

import com.papenko.project.entity.Bus;
import com.papenko.project.entity.Driver;

public class DriverBusChangeException extends RuntimeException {
    public DriverBusChangeException(Driver driver, Bus bus, Exception exception) {
        super("Could not assign driver " + driver + " to new bus " + bus, exception);
    }
}
