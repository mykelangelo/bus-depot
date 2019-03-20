package com.papenko.project.exception.route;

public class RouteCreationException extends RuntimeException {
    public RouteCreationException(String routeName, Exception exception) {
        super("Could not create a new route " + routeName, exception);
    }
}
