package com.papenko.project.exception.bus;

public class BusCreationException extends RuntimeException {
    public BusCreationException(String busSerial, Exception exception) {
        super("Could not create a new bus " + busSerial, exception);
    }
}
