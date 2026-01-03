package com.xianyu.marketplace.repository;

import com.xianyu.marketplace.entity.Order;
import com.xianyu.marketplace.entity.Product;
import com.xianyu.marketplace.entity.Review;
import com.xianyu.marketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct(Product product);
    List<Review> findByUser(User user);
    Optional<Review> findByOrder(Order order);
}
