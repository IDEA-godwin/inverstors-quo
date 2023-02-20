package com.sample.investorsquo.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;

    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "contact_phone")
    private String contactPhone;
    private String password;

    @Column(name = "email_verified")
    private boolean emailVerified;

    @Column(name = "email_verification_key", length = 10)
    private String emailVerificationKey;

    @Column(name = "date_of_birth", length = 30)
    private String dataOfBirth;

    @Column(length = 30)
    private String gender;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles",
        joinColumns = {@JoinColumn(name = "user_id")})
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;
}
