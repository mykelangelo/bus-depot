package com.papenko.project.exception.bus;

import com.papenko.project.entity.Bus;
import com.papenko.project.entity.Route;

public class BusRouteChangeException extends RuntimeException {
    public BusRouteChangeException(Bus bus, Route route, Exception exception) {
        super("Could not assign bus " + bus + " to new route " + route, exception);
    }
}
