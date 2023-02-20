package com.sample.investorsquo.domain.dto;

public record CreateKeycloakUserDto (
    String firstName,
    String lastName,
    String username,
    String email,
    String password,
    boolean enabled,
    boolean emailVerified
) {

}
