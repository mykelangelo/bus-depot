package com.papenko.project.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class AuthenticatedUserDetails {
    private String email;
    private UserType userType;

    public AuthenticatedUserDetails(String email, UserType userType) {
        this.email = email;
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("email", email)
                .append("userType", userType)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticatedUserDetails that = (AuthenticatedUserDetails) o;
        return new EqualsBuilder()
                .append(email, that.email)
                .append(userType, that.userType)
                .isEquals();
    }
}
