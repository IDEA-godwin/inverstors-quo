package com.sample.investorsquo.domain.mapper;

import com.sample.investorsquo.domain.dto.RegisterUserDto;
import com.sample.investorsquo.domain.dto.UserDto;
import com.sample.investorsquo.domain.entities.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDto UserToUserDto(User user);
    User RegisterUserDtoToUser(RegisterUserDto userDto);
}
