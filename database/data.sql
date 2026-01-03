-- ============================================
-- 学生二手闲鱼系统 - 示例数据
-- Student Second-Hand Marketplace System - Sample Data
-- ============================================

USE xianyudb;

-- ============================================
-- 清空现有数据 (Clear existing data)
-- ============================================
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE reviews;
TRUNCATE TABLE favorites;
TRUNCATE TABLE orders;
TRUNCATE TABLE addresses;
TRUNCATE TABLE products;
TRUNCATE TABLE users;
SET FOREIGN_KEY_CHECKS = 1;

-- ============================================
-- 插入示例用户 (Insert sample users)
-- 警告：这些密码是明文存储的，仅用于开发和测试！
-- WARNING: These passwords are stored in plain text for development/testing ONLY!
-- 生产环境必须使用加密密码（如 BCrypt）
-- Production environments MUST use encrypted passwords (e.g., BCrypt)
-- ============================================
INSERT INTO users (id, username, password, email, phone, avatar, created_at) VALUES
(1, '张三', 'password123', 'zhangsan@example.com', '13800138001', NULL, NOW()),
(2, '李四', 'password123', 'lisi@example.com', '13800138002', NULL, NOW());

-- ============================================
-- 插入示例收货地址 (Insert sample addresses)
-- ============================================
INSERT INTO addresses (id, user_id, receiver_name, phone, province, city, district, detail_address, is_default) VALUES
(1, 1, '张三', '13800138001', '北京市', '北京市', '海淀区', '清华大学', TRUE),
(2, 1, '张三', '13800138001', '北京市', '北京市', '朝阳区', '北京大学', FALSE);

-- ============================================
-- 插入示例商品 (Insert sample products)
-- ============================================
INSERT INTO products (id, title, description, price, category, `condition`, image_url, status, seller_id, created_at, updated_at) VALUES
(1, 'MacBook Pro 2020', '9成新，性能良好，无划痕，配件齐全。升级新电脑，低价转让。', 6800.00, '电子产品', '九成新', 
 'https://via.placeholder.com/400x300/4A90E2/FFFFFF?text=MacBook+Pro', 'available', 2, NOW(), NOW()),

(2, 'Java编程思想 第4版', '经典的Java学习书籍，8成新，有少许笔记，适合Java初学者。', 50.00, '图书教材', '八成新',
 'https://via.placeholder.com/400x300/E27A3F/FFFFFF?text=Java+Book', 'available', 1, NOW(), NOW()),

(3, '自行车 山地车', '7成新，适合校园骑行，刹车灵敏，变速正常。因毕业低价转让。', 380.00, '运动器材', '七成新',
 'https://via.placeholder.com/400x300/45B7AF/FFFFFF?text=Bicycle', 'available', 2, NOW(), NOW()),

(4, 'iPhone 13 128GB', '全新未拆封，京东购买多余，原价转让，可提供发票。', 4999.00, '电子产品', '全新',
 'https://via.placeholder.com/400x300/8E44AD/FFFFFF?text=iPhone+13', 'available', 1, NOW(), NOW()),

(5, '宿舍小书桌', '宿舍用折叠书桌，方便实用，8成新，尺寸60x40cm。', 80.00, '生活用品', '八成新',
 'https://via.placeholder.com/400x300/27AE60/FFFFFF?text=Desk', 'available', 2, NOW(), NOW()),

(6, 'Nike运动鞋 42码', '正品Nike运动鞋，穿过几次，9成新，鞋底无磨损，舒适透气。', 280.00, '服饰鞋包', '九成新',
 'https://via.placeholder.com/400x300/E74C3C/FFFFFF?text=Nike+Shoes', 'available', 1, NOW(), NOW());

-- ============================================
-- 重置自增ID (Reset auto-increment IDs)
-- ============================================
ALTER TABLE users AUTO_INCREMENT = 3;
ALTER TABLE addresses AUTO_INCREMENT = 3;
ALTER TABLE products AUTO_INCREMENT = 7;
ALTER TABLE orders AUTO_INCREMENT = 1;
ALTER TABLE favorites AUTO_INCREMENT = 1;
ALTER TABLE reviews AUTO_INCREMENT = 1;

-- ============================================
-- 显示插入结果 (Display insertion results)
-- ============================================
SELECT '用户数据插入完成' AS status, COUNT(*) AS count FROM users;
SELECT '地址数据插入完成' AS status, COUNT(*) AS count FROM addresses;
SELECT '商品数据插入完成' AS status, COUNT(*) AS count FROM products;
