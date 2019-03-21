package com.papenko.project.exception.bus;

import com.papenko.project.entity.Bus;

public class BusDeletionException extends RuntimeException {
    public BusDeletionException(Bus bus, Exception exception) {
        super("Could not delete bus " + bus, exception);
    }
}
