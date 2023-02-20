package com.sample.investorsquo.customException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

public record AuthenticationExceptionResponse (
    int statusCode,
    String error,
    String timestamp,
    String path
) {

    public String toJsonString() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
