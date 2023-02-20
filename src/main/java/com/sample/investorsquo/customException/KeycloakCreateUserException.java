package com.sample.investorsquo.customException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class KeycloakCreateUserException extends Exception {

    private String message;
    private int code;

    public KeycloakCreateUserException(String message) {
        super(message);
        this.message = message;
    }

    public KeycloakCreateUserException(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }
}
