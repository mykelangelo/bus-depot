package com.papenko.project.exception.bus;

import com.papenko.project.entity.Bus;

import java.util.List;

public class BusesSearchException extends RuntimeException {
    public BusesSearchException(List<Bus> buses, Exception exception) {
        super("Could not find all buses. Current drivers buses: " + buses, exception);
    }
}
