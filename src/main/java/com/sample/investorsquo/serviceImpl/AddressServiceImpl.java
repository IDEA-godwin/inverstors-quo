package com.sample.investorsquo.serviceImpl;

import com.sample.investorsquo.domain.entities.Address;
import com.sample.investorsquo.repository.AddressRepository;
import com.sample.investorsquo.service.AddressService;
import com.sample.investorsquo.serviceImpl.util.ServiceUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;


    @Override
    @Transactional
    public Address updateUserAddress(Address addressDto) {

        if (addressDto.getId() == null)
            return saveAddress(addressDto);

        Address address = getAddress(addressDto.getId());
        address.setNationality(ServiceUtil.updateField(address.getNationality(), addressDto.getNationality()));
        address.setZipCode(ServiceUtil.updateField(address.getZipCode(), addressDto.getZipCode()));
        address.setState(ServiceUtil.updateField(address.getState(), addressDto.getState()));
        address.setCity(ServiceUtil.updateField(address.getCity(), addressDto.getCity()));
        address.setLine1(ServiceUtil.updateField(address.getLine1(), addressDto.getLine1()));
        address.setLine2(ServiceUtil.updateField(address.getLine2(), addressDto.getLine2()));
        return saveAddress(address);
    }

    Address getAddress(Long addressId) {
        return addressRepository.findById(addressId).orElse(new Address());
    }

    Address saveAddress(Address address) {
        return addressRepository.save(address);
    }
}
