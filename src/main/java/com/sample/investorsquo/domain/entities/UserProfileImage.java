package com.sample.investorsquo.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@Entity
@Table(name = "users_avatar")
public class UserProfileImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String name;

    @Lob
    private byte[] avatar;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}
