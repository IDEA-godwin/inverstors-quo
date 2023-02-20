package com.sample.investorsquo.domain.entities;

import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {

    USER("USER"),
    ADMIN("ADMIN");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }


    @Override
    public String getAuthority() {
        return this.role;
    }
}
