package com.papenko.project.exception.bus;

public class BusSearchException extends RuntimeException {
    public BusSearchException(String busSerial, Exception exception) {
        super("Could not find bus by serial number " + busSerial, exception);
    }
}
