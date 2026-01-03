package com.xianyu.marketplace.service;

import com.xianyu.marketplace.entity.Order;
import com.xianyu.marketplace.entity.User;
import com.xianyu.marketplace.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductService productService;
    
    public Order createOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        // Update product status to pending when order is created
        productService.updateProductStatus(order.getProduct().getId(), "pending");
        return savedOrder;
    }
    
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    
    public Optional<Order> getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }
    
    public List<Order> getOrdersByBuyer(User buyer) {
        return orderRepository.findByBuyerOrderByCreatedAtDesc(buyer);
    }
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }
    
    public void updateOrderStatus(Long orderId, String status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(status);
            orderRepository.save(order);
            
            // Update product status based on order status
            if ("paid".equals(status)) {
                productService.updateProductStatus(order.getProduct().getId(), "sold");
            } else if ("cancelled".equals(status)) {
                productService.updateProductStatus(order.getProduct().getId(), "available");
            }
        }
    }
    
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
