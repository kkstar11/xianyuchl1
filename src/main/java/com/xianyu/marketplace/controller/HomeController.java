package com.xianyu.marketplace.controller;

import com.xianyu.marketplace.entity.Product;
import com.xianyu.marketplace.entity.User;
import com.xianyu.marketplace.service.ProductService;
import com.xianyu.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String home(Model model) {
        List<Product> products = productService.getAvailableProducts();
        model.addAttribute("products", products);
        
        // For demo purposes, get the first user or create a default one
        User currentUser = userService.getAllUsers().stream()
                .findFirst()
                .orElse(null);
        model.addAttribute("currentUser", currentUser);
        
        return "index";
    }
    
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
