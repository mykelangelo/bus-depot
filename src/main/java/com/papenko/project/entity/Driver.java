package com.papenko.project.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Driver {
    private final String userEmail;
    private final Bus bus;
    private final boolean awareOfAssignment;

    public Driver(String userEmail, Bus bus, boolean awareOfAssignment) {
        this.userEmail = userEmail;
        this.bus = bus;
        this.awareOfAssignment = awareOfAssignment;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Bus getBus() {
        return bus;
    }

    public boolean isAwareOfAssignment() {
        return awareOfAssignment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return new EqualsBuilder()
                .append(userEmail, driver.userEmail)
                .append(bus, driver.bus)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userEmail", userEmail)
                .append("bus", bus)
                .toString();
    }
}
