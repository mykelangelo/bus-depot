package com.papenko.project.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Driver {
    private final String userEmail;
    private final String busSerial;

    public Driver(String userEmail, String busSerial) {
        this.userEmail = userEmail;
        this.busSerial = busSerial;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getBusSerial() {
        return busSerial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return new EqualsBuilder()
                .append(userEmail, driver.userEmail)
                .append(busSerial, driver.busSerial)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userEmail", userEmail)
                .append("busSerial", busSerial)
                .toString();
    }
}
