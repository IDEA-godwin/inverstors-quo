package com.sample.investorsquo.service;

import com.sample.investorsquo.domain.entities.Address;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {

    Address updateUserAddress(Address address);
}
