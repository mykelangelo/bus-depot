package com.papenko.project.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class User {
    private final String email;
    private final UserType userType;
    private final String passwordHash;

    public User(String email, UserType userType, String passwordHash) {
        this.email = email;
        this.userType = userType;
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return new EqualsBuilder()
                .append(email, user.email)
                .append(userType, user.userType)
                .append(passwordHash, user.passwordHash)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("email", email)
                .append("userType", userType)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, userType, passwordHash);
    }
}
