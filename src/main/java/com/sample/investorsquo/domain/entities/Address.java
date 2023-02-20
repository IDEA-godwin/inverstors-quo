package com.sample.investorsquo.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    private String nationality;

    @Column(length = 100)
    private String state;

    @Column(length = 100)
    private String city;

    @Column(name = "line_2")
    private String line2;

    @Column(name = "line_1", length = 50)
    private String line1;

    @Column(name = "zip_code", length = 100)
    private String zipCode;
}
