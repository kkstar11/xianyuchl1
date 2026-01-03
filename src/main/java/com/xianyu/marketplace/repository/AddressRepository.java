package com.xianyu.marketplace.repository;

import com.xianyu.marketplace.entity.Address;
import com.xianyu.marketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(User user);
    Optional<Address> findByUserAndIsDefault(User user, Boolean isDefault);
}
