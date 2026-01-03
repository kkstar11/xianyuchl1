# 数据库文档 (Database Documentation)

## 概述 (Overview)

本目录包含学生二手闲鱼系统的数据库架构和初始化脚本。
This directory contains database schema and initialization scripts for the Student Second-Hand Marketplace System.

## 文件说明 (File Descriptions)

### schema.sql
数据库架构文件，包含所有表的创建语句。
Database schema file containing CREATE TABLE statements for all tables.

**包含的表 (Tables included):**
- `users` - 用户表
- `products` - 商品表
- `addresses` - 收货地址表
- `orders` - 订单表
- `favorites` - 收藏表
- `reviews` - 评价表

### data.sql
示例数据文件，包含初始化数据。
Sample data file containing initialization data.

**示例数据 (Sample data):**
- 2个示例用户
- 2个示例收货地址
- 6个示例商品（包括电子产品、图书、运动器材等）

## 数据库架构 (Database Schema)

### ER 关系图 (Entity Relationship)

```
users (用户)
  ├─→ products (商品) [seller_id]
  ├─→ addresses (地址) [user_id]
  ├─→ orders (订单) [buyer_id]
  ├─→ favorites (收藏) [user_id]
  └─→ reviews (评价) [user_id]

products (商品)
  ├─→ orders (订单) [product_id]
  ├─→ favorites (收藏) [product_id]
  └─→ reviews (评价) [product_id]

orders (订单)
  ├─→ reviews (评价) [order_id]
  └─→ addresses (地址) [address_id]
```

## 使用方法 (Usage)

### 开发环境 (Development Environment)

应用程序默认使用 **H2 内存数据库**，无需手动执行 SQL 脚本。
The application uses **H2 in-memory database** by default, no manual SQL execution needed.

配置文件：`src/main/resources/application.properties`
```properties
spring.datasource.url=jdbc:h2:mem:xianyudb
spring.jpa.hibernate.ddl-auto=update
```

启动应用后：
- 表结构会自动创建（通过 JPA Entity）
- 示例数据会自动初始化（通过 DataInitializer）
- 可以访问 H2 控制台：http://localhost:8080/h2-console

### 生产环境 (Production Environment)

对于生产环境，建议使用 **MySQL** 数据库。

#### 1. 创建数据库和表结构

```bash
# 登录 MySQL
mysql -u root -p

# 执行架构脚本
source /path/to/database/schema.sql
```

或者使用命令行直接导入：
```bash
mysql -u root -p < database/schema.sql
```

#### 2. 插入示例数据（可选）

```bash
mysql -u root -p < database/data.sql
```

#### 3. 配置应用程序

创建生产环境配置文件 `application-prod.properties`:

```properties
# 数据库配置 (Database Configuration)
spring.datasource.url=jdbc:mysql://localhost:3306/xianyudb?useSSL=false&serverTimezone=UTC&characterEncoding=utf8mb4
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA 配置 (JPA Configuration)
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

# 禁用 H2 控制台 (Disable H2 Console)
spring.h2.console.enabled=false
```

#### 4. 使用生产配置启动应用

```bash
java -jar xianyuchl1-1.0.0.jar --spring.profiles.active=prod
```

或使用 Maven：
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## 数据库迁移 (Database Migration)

### 从开发环境迁移到生产环境

1. **导出 H2 数据** (如果需要保留开发数据)
   - 访问 H2 控制台
   - 执行 `SCRIPT TO 'backup.sql'`

2. **导入到 MySQL**
   - 调整 SQL 语法（H2 和 MySQL 可能有差异）
   - 执行修改后的脚本

### 备份和恢复

#### 备份数据库
```bash
mysqldump -u root -p xianyudb > xianyudb_backup.sql
```

#### 恢复数据库
```bash
mysql -u root -p xianyudb < xianyudb_backup.sql
```

## 注意事项 (Notes)

1. **字符编码**: 数据库使用 UTF-8 (utf8mb4) 编码以支持中文和表情符号
2. **外键约束**: 使用 `ON DELETE CASCADE` 确保数据一致性
3. **索引优化**: 在常用查询字段上创建了索引以提高性能
4. **密码安全**: 
   - ⚠️ 示例数据中的密码是明文，**仅用于开发和测试**
   - 生产环境**必须**使用加密密码（推荐使用 BCrypt、Argon2 等）
   - Spring Security 提供了 `BCryptPasswordEncoder` 用于密码加密
5. **数据库用户权限**: 
   - ⚠️ 生产环境**不要**使用 root 账户连接数据库
   - 创建专用应用账户并授予最小必要权限
   - 示例：`CREATE USER 'xianyuapp'@'localhost' IDENTIFIED BY 'secure_password';`
   - 示例：`GRANT SELECT, INSERT, UPDATE, DELETE ON xianyudb.* TO 'xianyuapp'@'localhost';`
6. **DDL-AUTO**: 
   - 开发环境使用 `update` - 自动更新表结构
   - 生产环境建议使用 `validate` - 仅验证表结构，不自动修改

## 表结构详情 (Table Details)

### users (用户表)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键，自增 |
| username | VARCHAR(100) | 用户名，唯一 |
| password | VARCHAR(255) | 密码 |
| email | VARCHAR(100) | 邮箱 |
| phone | VARCHAR(20) | 手机号 |
| avatar | VARCHAR(500) | 头像URL |
| created_at | DATETIME | 创建时间 |

### products (商品表)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键，自增 |
| title | VARCHAR(200) | 商品标题 |
| description | TEXT | 商品描述 |
| price | DECIMAL(10,2) | 价格 |
| category | VARCHAR(50) | 分类 |
| condition | VARCHAR(50) | 新旧程度 |
| image_url | VARCHAR(500) | 图片URL |
| status | VARCHAR(20) | 状态 (available/sold/pending) |
| seller_id | BIGINT | 卖家ID (外键) |
| created_at | DATETIME | 创建时间 |
| updated_at | DATETIME | 更新时间 |

### addresses (收货地址表)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键，自增 |
| user_id | BIGINT | 用户ID (外键) |
| receiver_name | VARCHAR(100) | 收货人姓名 |
| phone | VARCHAR(20) | 联系电话 |
| province | VARCHAR(50) | 省份 |
| city | VARCHAR(50) | 城市 |
| district | VARCHAR(50) | 区县 |
| detail_address | VARCHAR(200) | 详细地址 |
| is_default | BOOLEAN | 是否默认地址 |

### orders (订单表)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键，自增 |
| order_number | VARCHAR(50) | 订单号，唯一 |
| buyer_id | BIGINT | 买家ID (外键) |
| product_id | BIGINT | 商品ID (外键) |
| address_id | BIGINT | 收货地址ID (外键) |
| total_amount | DECIMAL(10,2) | 订单总额 |
| status | VARCHAR(20) | 订单状态 |
| created_at | DATETIME | 创建时间 |
| updated_at | DATETIME | 更新时间 |

### favorites (收藏表)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键，自增 |
| user_id | BIGINT | 用户ID (外键) |
| product_id | BIGINT | 商品ID (外键) |
| created_at | DATETIME | 创建时间 |

### reviews (评价表)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键，自增 |
| order_id | BIGINT | 订单ID (外键) |
| user_id | BIGINT | 用户ID (外键) |
| product_id | BIGINT | 商品ID (外键) |
| rating | INT | 评分 (1-5星) |
| comment | TEXT | 评价内容 |
| created_at | DATETIME | 创建时间 |

## 技术支持 (Technical Support)

如有问题或建议，请提交 Issue 到项目仓库。
For questions or suggestions, please submit an Issue to the project repository.
