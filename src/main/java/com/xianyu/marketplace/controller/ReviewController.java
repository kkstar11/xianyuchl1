package com.xianyu.marketplace.controller;

import com.xianyu.marketplace.entity.Order;
import com.xianyu.marketplace.entity.Product;
import com.xianyu.marketplace.entity.Review;
import com.xianyu.marketplace.entity.User;
import com.xianyu.marketplace.service.OrderService;
import com.xianyu.marketplace.service.ProductService;
import com.xianyu.marketplace.service.ReviewService;
import com.xianyu.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/create/{orderId}")
    public String createReviewForm(@PathVariable Long orderId, Model model, RedirectAttributes redirectAttributes) {
        // Get current user (demo - using first user)
        User currentUser = userService.getAllUsers().stream()
                .findFirst()
                .orElse(null);
        
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("error", "请先登录");
            return "redirect:/orders";
        }
        
        Optional<Order> orderOpt = orderService.getOrderById(orderId);
        if (orderOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "订单不存在");
            return "redirect:/orders";
        }
        
        Order order = orderOpt.get();
        
        // Check if already reviewed
        if (reviewService.hasUserReviewedOrder(order)) {
            redirectAttributes.addFlashAttribute("error", "您已评价过此订单");
            return "redirect:/orders/" + orderId;
        }
        
        model.addAttribute("order", order);
        model.addAttribute("review", new Review());
        model.addAttribute("currentUser", currentUser);
        
        return "reviews/form";
    }
    
    @PostMapping("/create")
    public String createReview(@RequestParam Long orderId,
                             @RequestParam Integer rating,
                             @RequestParam String comment,
                             RedirectAttributes redirectAttributes) {
        
        // Get current user (demo - using first user)
        User currentUser = userService.getAllUsers().stream()
                .findFirst()
                .orElse(null);
        
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("error", "请先登录");
            return "redirect:/orders";
        }
        
        Optional<Order> orderOpt = orderService.getOrderById(orderId);
        if (orderOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "订单不存在");
            return "redirect:/orders";
        }
        
        Order order = orderOpt.get();
        
        // Check if already reviewed
        if (reviewService.hasUserReviewedOrder(order)) {
            redirectAttributes.addFlashAttribute("error", "您已评价过此订单");
            return "redirect:/orders/" + orderId;
        }
        
        Review review = new Review();
        review.setOrder(order);
        review.setUser(currentUser);
        review.setProduct(order.getProduct());
        review.setRating(rating);
        review.setComment(comment);
        
        reviewService.createReview(review);
        
        // Update order status to completed
        orderService.updateOrderStatus(orderId, "completed");
        
        redirectAttributes.addFlashAttribute("success", "评价成功！");
        return "redirect:/orders/" + orderId;
    }
}
