package com.sample.investorsquo.controller.restControllers;

import com.sample.investorsquo.controller.restControllers.Response.UserSuccessResponse;
import com.sample.investorsquo.controller.restControllers.viewManager.ChangePasswordVM;
import com.sample.investorsquo.customException.EmailAlreadyExistException;
import com.sample.investorsquo.domain.dto.RegisterUserDto;
import com.sample.investorsquo.domain.dto.UpdateProfileDto;
import com.sample.investorsquo.domain.dto.UserAccountInfo;
import com.sample.investorsquo.domain.dto.UserDto;
import com.sample.investorsquo.service.UserAdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RepositoryRestController(value = "/users")
@AllArgsConstructor
@Tag(name = "User Administration Controller")
public class UserAdminController {

    private final UserAdminService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<UserSuccessResponse> registerNewUser(@RequestBody RegisterUserDto userDto)
            throws EmailAlreadyExistException {
        userService.registerNewUser(userDto);
        return ResponseEntity.ok(new UserSuccessResponse(200, "successful"));
    }

    @RequestMapping(value = "/update-profile", method = RequestMethod.PATCH)
    public ResponseEntity<UserSuccessResponse> updateUserProfile(@RequestBody UpdateProfileDto profileDto) {
        UserDto userDto = userService.updateAccountProfile(profileDto);
        return ResponseEntity.ok(new UserSuccessResponse(200, "successful", userDto));
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity<UserSuccessResponse> me() {
        UserAccountInfo me = userService.me();
        return ResponseEntity.ok(new UserSuccessResponse(200, "successful", me));
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    public ResponseEntity<UserSuccessResponse> changePassword(@RequestBody ChangePasswordVM passwordVM) {
        userService.changePassword(passwordVM.password());
        return ResponseEntity.ok(new UserSuccessResponse(200, "successful"));
    }

}
