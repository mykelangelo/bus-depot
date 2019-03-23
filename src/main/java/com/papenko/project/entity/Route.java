package com.papenko.project.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;


public class Route {
    private final String name;

    public Route(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return new EqualsBuilder()
                .append(name, route.name)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
