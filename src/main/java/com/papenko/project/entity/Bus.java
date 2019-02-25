package com.papenko.project.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Bus {
    private final String serialNumber;

    private String routeName;

    public Bus(String serialNumber, String routeName) {
        this.serialNumber = serialNumber;
        this.routeName = routeName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bus bus = (Bus) o;
        return new EqualsBuilder()
                .append(serialNumber, bus.serialNumber)
                .append(routeName, bus.routeName)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("serialNumber", serialNumber)
                .append("routeName", routeName)
                .toString();
    }
}
