package com.airline.domain.account.valueObject;

import java.util.Objects;

public class Role {
    private final String value;

    public Role(String value) {
        this.value = value;
    }
    public static Role USER() {
        return new Role("USER");
    }

    public static Role ADMIN() {
        return new Role("ADMIN");
    }

    public static Role fromValue(String value) {
        if (!"USER".equals(value) && !"ADMIN".equals(value)) {
            throw new IllegalArgumentException("Invalid role: " + value);
        }
        return new Role(value);
    }
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return value.equals(role.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
