package com.sample.investorsquo.serviceImpl;

import com.sample.investorsquo.config.security.SecurityUtil;
import com.sample.investorsquo.customException.EmailAlreadyExistException;
import com.sample.investorsquo.customException.KeycloakCreateUserException;
import com.sample.investorsquo.domain.dto.*;
import com.sample.investorsquo.domain.entities.User;
import com.sample.investorsquo.domain.mapper.UserMapper;
import com.sample.investorsquo.repository.UserRepository;
import com.sample.investorsquo.service.AddressService;
import com.sample.investorsquo.service.KeycloakService;
import com.sample.investorsquo.service.UserAdminService;
import com.sample.investorsquo.serviceImpl.util.MailSenderService;
import com.sample.investorsquo.serviceImpl.util.ServiceUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserAdminServiceImpl implements UserAdminService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final KeycloakService keycloakService;
    private final AddressService addressService;
    private final MailSenderService mailSenderService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerNewUser(RegisterUserDto userDto) throws EmailAlreadyExistException {

        Optional<User> optionalUser = userRepository.findUserByEmailIgnoreCase(userDto.getEmail());
        if (optionalUser.isPresent())
            throw new EmailAlreadyExistException(String.format("User with email %s already exist", userDto.getEmail()));

        CreateKeycloakUserDto keycloakUserDto = new CreateKeycloakUserDto(
                userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(),
                userDto.getEmail(), userDto.getPassword(), true, false
        );

        try {
            String userId = keycloakService.createNewUser(keycloakUserDto);
            User user = userMapper.RegisterUserDtoToUser(userDto);
            user.setId(userId);
            saveUser(user);
            mailSenderService.sendMail(
                "Simple mail", "Hello there, here is a simple mail message",
                "godwinaquinas137@gmail.com",
                "godwinaquinas@gmail.com", "udochukwuigwurube@yahoo.com");
        } catch (KeycloakCreateUserException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(e.getCode()), e.getMessage());
        }
    }

    @Override
    public void changePassword(String newPassword) {
        String authSubject = SecurityUtil.bearerSubject();
        keycloakService.resetUserCredential(authSubject, newPassword);
        User user = userRepository.getReferenceById(authSubject);
        user.setPassword(passwordEncoder.encode(newPassword));
        saveUser(user);
    }

    @Override
    public UserDto updateAccountProfile(UpdateProfileDto profileDto) {
        User user = getCurrentUser();
        user.setContactPhone(ServiceUtil.updateField(user.getContactPhone(), profileDto.getContactPhone()));
        user.setDataOfBirth(ServiceUtil.updateField(user.getDataOfBirth(), profileDto.getDataOfBirth()));
        user.setGender(ServiceUtil.updateField(user.getGender(), profileDto.getGender()));
        user.setAddress(addressService.updateUserAddress(profileDto.getAddress()));
        return userMapper.UserToUserDto(saveUser(user));
    }

    @Override
    public void uploadProfileImage(UploadProfileImageDto profileImageDto) {

    }

    @Override
    public void deleteUserAccount() {

    }

    @Override
    public UserAccountInfo me() {
        User user = getCurrentUser();
        return new UserAccountInfo(userMapper.UserToUserDto(user));
    }

    @Override
    public String getProfileImage() {
        return null;
    }

    @Transactional
    User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    User getCurrentUser() {
        return userRepository.getReferenceById(SecurityUtil.bearerSubject());
    }
}
