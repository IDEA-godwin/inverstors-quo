package com.sample.investorsquo.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterUserDto {

    @NotNull(message = "field can not be empty")
    private String firstName;

    @NotNull(message = "field can not be empty")
    private String lastName;

    @Email
    @NotNull(message = "field can not be empty")
    private String email;

    @NotNull(message = "field can not be empty")
    @Size(min = 8, message = "password length should be greater than 8")
    private String password;
}
