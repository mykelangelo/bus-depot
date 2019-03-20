package com.papenko.project.exception.bus;

import com.papenko.project.entity.Bus;
import com.papenko.project.entity.Route;

import java.util.List;

public class BusesSearchException extends RuntimeException {
    public BusesSearchException(List<Bus> buses, Exception exception) {
        super("Could not find all buses. Current buses: " + buses, exception);
    }

    public BusesSearchException(List<Bus> buses, Route route, Exception exception) {
        super("Could not find buses on route " + route + ". Current buses: " + buses, exception);
    }
}
