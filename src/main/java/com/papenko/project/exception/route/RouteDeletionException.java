package com.papenko.project.exception.route;

import com.papenko.project.entity.Route;

public class RouteDeletionException extends RuntimeException {
    public RouteDeletionException(Route route, Exception exception) {
        super("Could not delete route " + route, exception);
    }
}
