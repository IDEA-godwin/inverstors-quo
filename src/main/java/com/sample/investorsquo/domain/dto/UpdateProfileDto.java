package com.sample.investorsquo.domain.dto;

import com.sample.investorsquo.domain.entities.Address;
import com.sample.investorsquo.domain.entities.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class UpdateProfileDto {

    private String contactPhone;
    private String dataOfBirth;
    private String gender;
    private Set<Role> roles;
    private Address address;
}
