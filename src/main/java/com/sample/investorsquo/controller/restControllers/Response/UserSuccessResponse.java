package com.sample.investorsquo.controller.restControllers.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSuccessResponse {

    private Integer status;
    private String message;
    private Object body;

    public UserSuccessResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
