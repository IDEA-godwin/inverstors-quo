package com.sample.investorsquo.config.handlers;

import com.sample.investorsquo.customException.AuthenticationExceptionResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class CustomAccessDeniedExceptionHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                                        AccessDeniedException exception) throws IOException, ServletException {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        AuthenticationExceptionResponse body = new AuthenticationExceptionResponse(
                HttpStatus.FORBIDDEN.value(), exception.getMessage(),
                new Date().toString(), request.getContextPath()
        );
        response.getOutputStream().println(body.toJsonString());
    }


}
