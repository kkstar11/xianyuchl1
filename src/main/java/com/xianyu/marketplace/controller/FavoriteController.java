package com.xianyu.marketplace.controller;

import com.xianyu.marketplace.entity.Favorite;
import com.xianyu.marketplace.entity.Product;
import com.xianyu.marketplace.entity.User;
import com.xianyu.marketplace.service.FavoriteService;
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
@RequestMapping("/favorites")
public class FavoriteController {
    
    @Autowired
    private FavoriteService favoriteService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String listFavorites(Model model) {
        // Get current user (demo - using first user)
        User currentUser = userService.getAllUsers().stream()
                .findFirst()
                .orElse(null);
        
        if (currentUser == null) {
            return "redirect:/";
        }
        
        List<Favorite> favorites = favoriteService.getUserFavorites(currentUser);
        model.addAttribute("favorites", favorites);
        model.addAttribute("currentUser", currentUser);
        
        return "favorites/list";
    }
    
    @PostMapping("/add/{productId}")
    public String addFavorite(@PathVariable Long productId, RedirectAttributes redirectAttributes) {
        // Get current user (demo - using first user)
        User currentUser = userService.getAllUsers().stream()
                .findFirst()
                .orElse(null);
        
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("error", "请先登录");
            return "redirect:/products/" + productId;
        }
        
        Optional<Product> productOpt = productService.getProductById(productId);
        if (productOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "商品不存在");
            return "redirect:/products";
        }
        
        favoriteService.addFavorite(currentUser, productOpt.get());
        redirectAttributes.addFlashAttribute("success", "收藏成功！");
        
        return "redirect:/products/" + productId;
    }
    
    @PostMapping("/remove/{productId}")
    public String removeFavorite(@PathVariable Long productId, 
                                @RequestParam(required = false) String returnUrl,
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
        if (productOpt.isPresent()) {
            favoriteService.removeFavorite(currentUser, productOpt.get());
            redirectAttributes.addFlashAttribute("success", "取消收藏成功！");
        }
        
        if (returnUrl != null && !returnUrl.isEmpty()) {
            return "redirect:" + returnUrl;
        }
        return "redirect:/favorites";
    }
}
