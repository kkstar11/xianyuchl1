package com.xianyu.marketplace.controller;

import com.xianyu.marketplace.entity.Address;
import com.xianyu.marketplace.entity.User;
import com.xianyu.marketplace.service.AddressService;
import com.xianyu.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/addresses")
public class AddressController {
    
    @Autowired
    private AddressService addressService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String listAddresses(Model model) {
        // Get current user (demo - using first user)
        User currentUser = userService.getAllUsers().stream()
                .findFirst()
                .orElse(null);
        
        if (currentUser == null) {
            return "redirect:/";
        }
        
        List<Address> addresses = addressService.getAddressesByUser(currentUser);
        model.addAttribute("addresses", addresses);
        model.addAttribute("currentUser", currentUser);
        
        return "addresses/list";
    }
    
    @GetMapping("/add")
    public String addAddressForm(Model model) {
        model.addAttribute("address", new Address());
        
        // Get current user
        User currentUser = userService.getAllUsers().stream()
                .findFirst()
                .orElse(null);
        model.addAttribute("currentUser", currentUser);
        
        return "addresses/form";
    }
    
    @PostMapping("/add")
    public String addAddress(@RequestParam String receiverName,
                           @RequestParam String phone,
                           @RequestParam String province,
                           @RequestParam String city,
                           @RequestParam String district,
                           @RequestParam String detailAddress,
                           @RequestParam(required = false) Boolean isDefault,
                           RedirectAttributes redirectAttributes) {
        
        // Get current user (demo - using first user)
        User currentUser = userService.getAllUsers().stream()
                .findFirst()
                .orElse(null);
        
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("error", "请先登录");
            return "redirect:/addresses";
        }
        
        Address address = new Address();
        address.setUser(currentUser);
        address.setReceiverName(receiverName);
        address.setPhone(phone);
        address.setProvince(province);
        address.setCity(city);
        address.setDistrict(district);
        address.setDetailAddress(detailAddress);
        address.setIsDefault(isDefault != null ? isDefault : false);
        
        addressService.createAddress(address);
        
        redirectAttributes.addFlashAttribute("success", "地址添加成功！");
        return "redirect:/addresses";
    }
    
    @GetMapping("/edit/{id}")
    public String editAddressForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Address> addressOpt = addressService.getAddressById(id);
        if (addressOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "地址不存在");
            return "redirect:/addresses";
        }
        
        model.addAttribute("address", addressOpt.get());
        
        // Get current user
        User currentUser = userService.getAllUsers().stream()
                .findFirst()
                .orElse(null);
        model.addAttribute("currentUser", currentUser);
        
        return "addresses/form";
    }
    
    @PostMapping("/edit/{id}")
    public String editAddress(@PathVariable Long id,
                            @RequestParam String receiverName,
                            @RequestParam String phone,
                            @RequestParam String province,
                            @RequestParam String city,
                            @RequestParam String district,
                            @RequestParam String detailAddress,
                            @RequestParam(required = false) Boolean isDefault,
                            RedirectAttributes redirectAttributes) {
        
        Optional<Address> addressOpt = addressService.getAddressById(id);
        if (addressOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "地址不存在");
            return "redirect:/addresses";
        }
        
        Address address = addressOpt.get();
        address.setReceiverName(receiverName);
        address.setPhone(phone);
        address.setProvince(province);
        address.setCity(city);
        address.setDistrict(district);
        address.setDetailAddress(detailAddress);
        address.setIsDefault(isDefault != null ? isDefault : false);
        
        addressService.updateAddress(address);
        
        redirectAttributes.addFlashAttribute("success", "地址更新成功！");
        return "redirect:/addresses";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteAddress(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        addressService.deleteAddress(id);
        redirectAttributes.addFlashAttribute("success", "地址删除成功！");
        return "redirect:/addresses";
    }
}
