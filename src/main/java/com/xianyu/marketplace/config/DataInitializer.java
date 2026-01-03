package com.xianyu.marketplace.config;

import com.xianyu.marketplace.entity.Address;
import com.xianyu.marketplace.entity.Product;
import com.xianyu.marketplace.entity.User;
import com.xianyu.marketplace.service.AddressService;
import com.xianyu.marketplace.service.ProductService;
import com.xianyu.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AddressService addressService;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (!userService.getAllUsers().isEmpty()) {
            return;
        }

        // Create demo users
        User user1 = new User();
        user1.setUsername("张三");
        user1.setEmail("zhangsan@example.com");
        user1.setPassword("password123");
        user1.setPhone("13800138001");
        user1 = userService.createUser(user1);

        User user2 = new User();
        user2.setUsername("李四");
        user2.setEmail("lisi@example.com");
        user2.setPassword("password123");
        user2.setPhone("13800138002");
        user2 = userService.createUser(user2);

        // Create demo addresses
        Address address1 = new Address();
        address1.setUser(user1);
        address1.setReceiverName("张三");
        address1.setPhone("13800138001");
        address1.setProvince("北京市");
        address1.setCity("北京市");
        address1.setDistrict("海淀区");
        address1.setDetailAddress("清华大学");
        address1.setIsDefault(true);
        addressService.createAddress(address1);

        Address address2 = new Address();
        address2.setUser(user1);
        address2.setReceiverName("张三");
        address2.setPhone("13800138001");
        address2.setProvince("北京市");
        address2.setCity("北京市");
        address2.setDistrict("朝阳区");
        address2.setDetailAddress("北京大学");
        address2.setIsDefault(false);
        addressService.createAddress(address2);

        // Create demo products
        Product product1 = new Product();
        product1.setTitle("MacBook Pro 2020");
        product1.setDescription("9成新，性能良好，无划痕，配件齐全。升级新电脑，低价转让。");
        product1.setPrice(new BigDecimal("6800.00"));
        product1.setCategory("电子产品");
        product1.setCondition("九成新");
        product1.setImageUrl("https://via.placeholder.com/400x300/4A90E2/FFFFFF?text=MacBook+Pro");
        product1.setSeller(user2);
        product1.setStatus("available");
        productService.createProduct(product1);

        Product product2 = new Product();
        product2.setTitle("Java编程思想 第4版");
        product2.setDescription("经典的Java学习书籍，8成新，有少许笔记，适合Java初学者。");
        product2.setPrice(new BigDecimal("50.00"));
        product2.setCategory("图书教材");
        product2.setCondition("八成新");
        product2.setImageUrl("https://via.placeholder.com/400x300/E27A3F/FFFFFF?text=Java+Book");
        product2.setSeller(user1);
        product2.setStatus("available");
        productService.createProduct(product2);

        Product product3 = new Product();
        product3.setTitle("自行车 山地车");
        product3.setDescription("7成新，适合校园骑行，刹车灵敏，变速正常。因毕业低价转让。");
        product3.setPrice(new BigDecimal("380.00"));
        product3.setCategory("运动器材");
        product3.setCondition("七成新");
        product3.setImageUrl("https://via.placeholder.com/400x300/45B7AF/FFFFFF?text=Bicycle");
        product3.setSeller(user2);
        product3.setStatus("available");
        productService.createProduct(product3);

        Product product4 = new Product();
        product4.setTitle("iPhone 13 128GB");
        product4.setDescription("全新未拆封，京东购买多余，原价转让，可提供发票。");
        product4.setPrice(new BigDecimal("4999.00"));
        product4.setCategory("电子产品");
        product4.setCondition("全新");
        product4.setImageUrl("https://via.placeholder.com/400x300/8E44AD/FFFFFF?text=iPhone+13");
        product4.setSeller(user1);
        product4.setStatus("available");
        productService.createProduct(product4);

        Product product5 = new Product();
        product5.setTitle("宿舍小书桌");
        product5.setDescription("宿舍用折叠书桌，方便实用，8成新，尺寸60x40cm。");
        product5.setPrice(new BigDecimal("80.00"));
        product5.setCategory("生活用品");
        product5.setCondition("八成新");
        product5.setImageUrl("https://via.placeholder.com/400x300/27AE60/FFFFFF?text=Desk");
        product5.setSeller(user2);
        product5.setStatus("available");
        productService.createProduct(product5);

        Product product6 = new Product();
        product6.setTitle("Nike运动鞋 42码");
        product6.setDescription("正品Nike运动鞋，穿过几次，9成新，鞋底无磨损，舒适透气。");
        product6.setPrice(new BigDecimal("280.00"));
        product6.setCategory("服饰鞋包");
        product6.setCondition("九成新");
        product6.setImageUrl("https://via.placeholder.com/400x300/E74C3C/FFFFFF?text=Nike+Shoes");
        product6.setSeller(user1);
        product6.setStatus("available");
        productService.createProduct(product6);

        System.out.println("Sample data initialized successfully!");
    }
}
