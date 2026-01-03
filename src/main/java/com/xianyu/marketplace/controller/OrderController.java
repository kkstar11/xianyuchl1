package com.xianyu.marketplace.controller;

import com.xianyu.marketplace.entity.Address;
import com.xianyu.marketplace.entity.Order;
import com.xianyu.marketplace.entity.Product;
import com.xianyu.marketplace.entity.User;
import com.xianyu.marketplace.service.AddressService;
import com.xianyu.marketplace.service.OrderService;
import com.xianyu.marketplace.service.ProductService;
import com.xianyu.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private AddressService addressService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String listOrders(Model model) {
        // Get current user (demo - using first user)
        User currentUser = userService.getAllUsers().stream()
                .findFirst()
                .orElse(null);
        
        if (currentUser == null) {
            return "redirect:/";
        }
        
        List<Order> orders = orderService.getOrdersByBuyer(currentUser);
        model.addAttribute("orders", orders);
        model.addAttribute("currentUser", currentUser);
        
        return "orders/list";
    }
    
    @GetMapping("/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        Optional<Order> orderOpt = orderService.getOrderById(id);
        if (orderOpt.isEmpty()) {
            return "redirect:/orders";
        }
        
        Order order = orderOpt.get();
        model.addAttribute("order", order);
        
        // Get current user
        User currentUser = userService.getAllUsers().stream()
                .findFirst()
                .orElse(null);
        model.addAttribute("currentUser", currentUser);
        
        return "orders/detail";
    }
    
    @GetMapping("/create/{productId}")
    public String createOrderForm(@PathVariable Long productId, Model model, RedirectAttributes redirectAttributes) {
        // Get current user (demo - using first user)
        User currentUser = userService.getAllUsers().stream()
                .findFirst()
                .orElse(null);
        
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("error", "请先登录");
            return "redirect:/products/" + productId;
        }
        
        Optional<Product> productOpt = productService.getProductById(productId);
        if (productOpt.isEmpty() || !"available".equals(productOpt.get().getStatus())) {
            redirectAttributes.addFlashAttribute("error", "商品不可用");
            return "redirect:/products";
        }
        
        Product product = productOpt.get();
        List<Address> addresses = addressService.getAddressesByUser(currentUser);
        
        model.addAttribute("product", product);
        model.addAttribute("addresses", addresses);
        model.addAttribute("currentUser", currentUser);
        
        return "orders/create";
    }
    
    @PostMapping("/create")
    public String createOrder(@RequestParam Long productId,
                            @RequestParam Long addressId,
                            RedirectAttributes redirectAttributes) {
        
        // Get current user (demo - using first user)
        User currentUser = userService.getAllUsers().stream()
                .findFirst()
                .orElse(null);
        
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("error", "请先登录");
            return "redirect:/products/" + productId;
        }
        
        Optional<Product> productOpt = productService.getProductById(productId);
        Optional<Address> addressOpt = addressService.getAddressById(addressId);
        
        if (productOpt.isEmpty() || addressOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "订单创建失败");
            return "redirect:/products/" + productId;
        }
        
        Product product = productOpt.get();
        Address address = addressOpt.get();
        
        Order order = new Order();
        order.setBuyer(currentUser);
        order.setProduct(product);
        order.setAddress(address);
        order.setTotalAmount(product.getPrice());
        order.setStatus("pending");
        
        orderService.createOrder(order);
        
        redirectAttributes.addFlashAttribute("success", "订单创建成功！");
        return "redirect:/orders";
    }
    
    @PostMapping("/{id}/pay")
    public String payOrder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        orderService.updateOrderStatus(id, "paid");
        redirectAttributes.addFlashAttribute("success", "支付成功！");
        return "redirect:/orders/" + id;
    }
    
    @PostMapping("/{id}/cancel")
    public String cancelOrder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        orderService.updateOrderStatus(id, "cancelled");
        redirectAttributes.addFlashAttribute("success", "订单已取消");
        return "redirect:/orders/" + id;
    }
}
