package com.sample.investorsquo.domain.dto;

import com.sample.investorsquo.domain.entities.Address;
import com.sample.investorsquo.domain.entities.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.sample.investorsquo.domain.entities.User} entity
 */
@Data
public class UserDto implements Serializable {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String contactPhone;
    private final boolean emailVerified;
    private final String dataOfBirth;
    private final String gender;
    private final Set<Role> roles;
    private final Address address;
}
