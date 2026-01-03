package com.xianyu.marketplace.service;

import com.xianyu.marketplace.entity.Address;
import com.xianyu.marketplace.entity.User;
import com.xianyu.marketplace.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AddressService {
    
    @Autowired
    private AddressRepository addressRepository;
    
    public Address createAddress(Address address) {
        // If this is set as default, unset other default addresses for the user
        if (address.getIsDefault() != null && address.getIsDefault()) {
            List<Address> userAddresses = addressRepository.findByUser(address.getUser());
            for (Address addr : userAddresses) {
                if (addr.getIsDefault()) {
                    addr.setIsDefault(false);
                    addressRepository.save(addr);
                }
            }
        }
        return addressRepository.save(address);
    }
    
    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }
    
    public List<Address> getAddressesByUser(User user) {
        return addressRepository.findByUser(user);
    }
    
    public Optional<Address> getDefaultAddress(User user) {
        return addressRepository.findByUserAndIsDefault(user, true);
    }
    
    public Address updateAddress(Address address) {
        // If this is set as default, unset other default addresses for the user
        if (address.getIsDefault() != null && address.getIsDefault()) {
            List<Address> userAddresses = addressRepository.findByUser(address.getUser());
            for (Address addr : userAddresses) {
                if (!addr.getId().equals(address.getId()) && addr.getIsDefault()) {
                    addr.setIsDefault(false);
                    addressRepository.save(addr);
                }
            }
        }
        return addressRepository.save(address);
    }
    
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
