package com.xianyu.marketplace.config;

import com.xianyu.marketplace.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        
        // Public pages that don't require login
        String uri = request.getRequestURI();
        if (uri.equals("/") || uri.equals("/products") || uri.startsWith("/products/") && !uri.startsWith("/products/publish")) {
            return true;
        }
        
        // If user is not logged in and trying to access protected pages, redirect to login
        if (currentUser == null) {
            response.sendRedirect("/auth/login");
            return false;
        }
        
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("currentUser");
            if (currentUser != null) {
                modelAndView.addObject("currentUser", currentUser);
            }
        }
    }
}
