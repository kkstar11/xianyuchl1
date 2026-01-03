package com.xianyu.marketplace.repository;

import com.xianyu.marketplace.entity.Order;
import com.xianyu.marketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByBuyer(User buyer);
    List<Order> findByBuyerOrderByCreatedAtDesc(User buyer);
    Optional<Order> findByOrderNumber(String orderNumber);
}
