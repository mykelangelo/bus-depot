package com.papenko.project.exception.route;

public class RouteSearchException extends RuntimeException {
    public RouteSearchException(String routeName, Exception exception) {
        super("Could not find a route " + routeName, exception);
    }
}
