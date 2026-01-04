package com.xianyu.marketplace.controller;

import com.xianyu.marketplace.entity.Product;
import com.xianyu.marketplace.entity.Review;
import com.xianyu.marketplace.entity.User;
import com.xianyu.marketplace.service.FavoriteService;
import com.xianyu.marketplace.service.ProductService;
import com.xianyu.marketplace.service.ReviewService;
import com.xianyu.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private FavoriteService favoriteService;
    
    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productService.getAvailableProducts();
        model.addAttribute("products", products);
        return "products/list";
    }
    
    @GetMapping("/{id}")
    public String productDetail(@PathVariable Long id, Model model, HttpSession session) {
        Optional<Product> productOpt = productService.getProductById(id);
        if (productOpt.isEmpty()) {
            return "redirect:/products";
        }
        
        Product product = productOpt.get();
        model.addAttribute("product", product);
        
        // Get reviews for this product
        List<Review> reviews = reviewService.getReviewsByProduct(product);
        model.addAttribute("reviews", reviews);
        
        // Check if favorited (get from session)
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser != null) {
            boolean isFavorited = favoriteService.isFavorited(currentUser, product);
            model.addAttribute("isFavorited", isFavorited);
            model.addAttribute("currentUser", currentUser);
        }
        
        return "products/detail";
    }
    
    @GetMapping("/publish")
    public String publishForm(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("error", "请先登录");
            return "redirect:/auth/login";
        }
        
        model.addAttribute("product", new Product());
        model.addAttribute("currentUser", currentUser);
        
        return "products/publish";
    }
    
    @PostMapping("/publish")
    public String publishProduct(@RequestParam String title,
                                @RequestParam String description,
                                @RequestParam BigDecimal price,
                                @RequestParam String category,
                                @RequestParam String condition,
                                @RequestParam(required = false) String imageUrl,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        
        // Get current user from session
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("error", "请先登录");
            return "redirect:/auth/login";
        }
        
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(category);
        product.setCondition(condition);
        product.setImageUrl(imageUrl != null && !imageUrl.isEmpty() ? imageUrl : "/images/default-product.jpg");
        product.setSeller(currentUser);
        product.setStatus("available");
        
        productService.createProduct(product);
        
        redirectAttributes.addFlashAttribute("success", "商品发布成功！");
        return "redirect:/products";
    }
    
    @GetMapping("/my")
    public String myProducts(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        // Get current user from session
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("error", "请先登录");
            return "redirect:/auth/login";
        }
        
        List<Product> products = productService.getProductsBySeller(currentUser);
        model.addAttribute("products", products);
        model.addAttribute("currentUser", currentUser);
        
        return "products/my-products";
    }
}
