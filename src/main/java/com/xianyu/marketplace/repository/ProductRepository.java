package com.xianyu.marketplace.repository;

import com.xianyu.marketplace.entity.Product;
import com.xianyu.marketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatus(String status);
    List<Product> findBySeller(User seller);
    List<Product> findByCategory(String category);
    List<Product> findByStatusOrderByCreatedAtDesc(String status);
}
