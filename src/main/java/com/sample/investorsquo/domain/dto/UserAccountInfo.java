package com.sample.investorsquo.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UserAccountInfo {

    private UserDto profile;

    public UserAccountInfo(UserDto user) {
        this.profile = user;
    }

}
