package com.papenko.project.exception.route;

import com.papenko.project.entity.Route;

import java.util.List;

public class RoutesSearchException extends RuntimeException {
    public RoutesSearchException(List<Route> routes, Exception exception) {
        super("Could not find all routes. Current routes found: " + routes, exception);
    }
}
