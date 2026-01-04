package com.xianyu.marketplace.controller;

import com.xianyu.marketplace.entity.Product;
import com.xianyu.marketplace.entity.User;
import com.xianyu.marketplace.service.ProductService;
import com.xianyu.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public String home(Model model, HttpSession session) {
        List<Product> products = productService.getAvailableProducts();
        model.addAttribute("products", products);
        
        // Get current user from session
        User currentUser = (User) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        
        return "index";
    }
    
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
