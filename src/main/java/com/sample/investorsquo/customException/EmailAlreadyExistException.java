package com.sample.investorsquo.customException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailAlreadyExistException extends Exception {

    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
