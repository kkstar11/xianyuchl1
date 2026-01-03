package com.xianyu.marketplace.service;

import com.xianyu.marketplace.entity.Order;
import com.xianyu.marketplace.entity.Product;
import com.xianyu.marketplace.entity.Review;
import com.xianyu.marketplace.entity.User;
import com.xianyu.marketplace.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }
    
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }
    
    public List<Review> getReviewsByProduct(Product product) {
        return reviewRepository.findByProduct(product);
    }
    
    public List<Review> getReviewsByUser(User user) {
        return reviewRepository.findByUser(user);
    }
    
    public Optional<Review> getReviewByOrder(Order order) {
        return reviewRepository.findByOrder(order);
    }
    
    public boolean hasUserReviewedOrder(Order order) {
        return reviewRepository.findByOrder(order).isPresent();
    }
}
