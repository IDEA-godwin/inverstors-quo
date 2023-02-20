package com.sample.investorsquo.service;

import com.sample.investorsquo.customException.KeycloakCreateUserException;
import com.sample.investorsquo.domain.dto.CreateKeycloakUserDto;
import org.springframework.stereotype.Service;

@Service
public interface KeycloakService {

    String createNewUser(CreateKeycloakUserDto userDto) throws KeycloakCreateUserException;
    void resetUserCredential(String userId, String newPassword);
}
