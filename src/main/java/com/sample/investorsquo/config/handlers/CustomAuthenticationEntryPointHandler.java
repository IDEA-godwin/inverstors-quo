package com.sample.investorsquo.config.handlers;

import com.sample.investorsquo.customException.AuthenticationExceptionResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        AuthenticationExceptionResponse body = new AuthenticationExceptionResponse(
                HttpStatus.UNAUTHORIZED.value(), authException.getMessage(),
                new Date().toString(), request.getRequestURI()
        );
        response.getOutputStream().println(body.toJsonString());
    }
}
