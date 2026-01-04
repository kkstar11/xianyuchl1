package com.xianyu.marketplace.controller;

import com.xianyu.marketplace.entity.User;
import com.xianyu.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String loginForm(Model model, HttpSession session) {
        // If already logged in, redirect to home
        if (session.getAttribute("currentUser") != null) {
            return "redirect:/";
        }
        return "auth/login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String username,
                       @RequestParam String password,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        
        Optional<User> userOpt = userService.getUserByUsername(username);
        
        if (userOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "用户名不存在");
            return "redirect:/auth/login";
        }
        
        User user = userOpt.get();
        
        // Simple password check (in production, use BCrypt or similar)
        if (!user.getPassword().equals(password)) {
            redirectAttributes.addFlashAttribute("error", "密码错误");
            return "redirect:/auth/login";
        }
        
        // Store user in session
        session.setAttribute("currentUser", user);
        redirectAttributes.addFlashAttribute("success", "登录成功！欢迎 " + user.getUsername());
        
        return "redirect:/";
    }
    
    @GetMapping("/register")
    public String registerForm(Model model, HttpSession session) {
        // If already logged in, redirect to home
        if (session.getAttribute("currentUser") != null) {
            return "redirect:/";
        }
        model.addAttribute("user", new User());
        return "auth/register";
    }
    
    @PostMapping("/register")
    public String register(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String email,
                          @RequestParam(required = false) String phone,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        
        // Check if username already exists
        if (userService.getUserByUsername(username).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "用户名已存在");
            return "redirect:/auth/register";
        }
        
        // Check if email already exists
        if (userService.getUserByEmail(email).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "邮箱已被注册");
            return "redirect:/auth/register";
        }
        
        // Create new user
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // In production, hash the password
        user.setEmail(email);
        user.setPhone(phone);
        
        User savedUser = userService.createUser(user);
        
        // Auto-login after registration
        session.setAttribute("currentUser", savedUser);
        redirectAttributes.addFlashAttribute("success", "注册成功！欢迎 " + savedUser.getUsername());
        
        return "redirect:/";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute("currentUser");
        redirectAttributes.addFlashAttribute("success", "已成功退出登录");
        return "redirect:/";
    }
}
