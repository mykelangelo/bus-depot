package com.papenko.project.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Bus {
    private final String serialNumber;

    private final Route route;

    public Bus(String serialNumber, Route route) {
        this.serialNumber = serialNumber;
        this.route = route;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Route getRoute() {
        return route;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bus bus = (Bus) o;
        return new EqualsBuilder()
                .append(serialNumber, bus.serialNumber)
                .append(route, bus.route)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("serialNumber", serialNumber)
                .append("route", route)
                .toString();
    }
}
