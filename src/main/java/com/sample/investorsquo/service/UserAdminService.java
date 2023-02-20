package com.sample.investorsquo.service;

import com.sample.investorsquo.customException.EmailAlreadyExistException;
import com.sample.investorsquo.domain.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface UserAdminService {

    void registerNewUser(RegisterUserDto userDto) throws EmailAlreadyExistException;
    void changePassword(String newPassword);
    UserDto updateAccountProfile(UpdateProfileDto profileDto);
    void uploadProfileImage(UploadProfileImageDto profileImageDto);
    void deleteUserAccount();

    UserAccountInfo me();
    String getProfileImage();
}
