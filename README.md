# xianyuchl1 - 学生二手闲鱼系统

学生二手闲鱼系统，让用户能够正常收藏商品，下单，选择收货地址，查看订单，评价，以及发布商品这一系列操作。

A student second-hand marketplace system that allows users to browse, favorite, purchase products, manage shipping addresses, view orders, write reviews, and publish products.

## 功能特性 (Features)

- 🛍️ **商品浏览** - 浏览和搜索二手商品
- ⭐ **收藏功能** - 收藏感兴趣的商品
- 🛒 **下单购买** - 创建订单并购买商品
- 📍 **地址管理** - 添加和管理收货地址
- 📦 **订单管理** - 查看和跟踪订单状态
- ⭐ **商品评价** - 对购买的商品进行评价
- 📝 **发布商品** - 发布自己的二手商品

## 技术栈 (Tech Stack)

- **后端框架**: Spring Boot 2.7.14
- **Web框架**: Spring MVC
- **模板引擎**: Thymeleaf
- **ORM框架**: Spring Data JPA / Hibernate
- **开发数据库**: H2 Database (内存数据库)
- **生产数据库**: MySQL 8.x
- **构建工具**: Maven
- **Java版本**: 1.8+

## 数据库 (Database)

本项目包含完整的数据库架构和示例数据。详细信息请查看 [database/README.md](database/README.md)。

### 数据库表结构 (Database Schema)

- **users** - 用户表
- **products** - 商品表
- **addresses** - 收货地址表
- **orders** - 订单表
- **favorites** - 收藏表
- **reviews** - 评价表

### 快速开始 (Quick Start)

#### 开发环境

项目默认使用 H2 内存数据库，无需额外配置：

```bash
# 克隆项目
git clone https://github.com/kkstar11/xianyuchl1.git
cd xianyuchl1

# 构建并运行
mvn clean install
mvn spring-boot:run
```

访问应用: http://localhost:8080

访问 H2 控制台: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:xianyudb`
- Username: `sa`
- Password: (留空)

#### 生产环境

使用 MySQL 数据库部署：

1. **创建数据库**
```bash
mysql -u root -p < database/schema.sql
mysql -u root -p < database/data.sql  # 可选：插入示例数据
```

2. **配置数据库连接**

复制 `src/main/resources/application-prod.properties` 并修改数据库配置：
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/xianyudb
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. **使用生产配置启动**
```bash
java -jar target/xianyuchl1-1.0.0.jar --spring.profiles.active=prod
```

## 项目结构 (Project Structure)

```
xianyuchl1/
├── database/                    # 数据库脚本
│   ├── README.md               # 数据库文档
│   ├── schema.sql              # 数据库架构
│   └── data.sql                # 示例数据
├── src/
│   └── main/
│       ├── java/
│       │   └── com/xianyu/marketplace/
│       │       ├── config/     # 配置类
│       │       ├── controller/ # 控制器
│       │       ├── entity/     # 实体类
│       │       ├── repository/ # 数据访问层
│       │       └── service/    # 业务逻辑层
│       └── resources/
│           ├── static/         # 静态资源
│           ├── templates/      # Thymeleaf 模板
│           ├── application.properties          # 开发环境配置
│           └── application-prod.properties     # 生产环境配置
├── pom.xml                     # Maven 配置
└── README.md                   # 项目说明
```

## 开发说明 (Development Notes)

### 构建项目
```bash
mvn clean package
```

### 运行测试
```bash
mvn test
```

### 访问应用
- 应用首页: http://localhost:8080
- H2 数据库控制台: http://localhost:8080/h2-console

## 贡献 (Contributing)

欢迎提交 Issue 和 Pull Request！

## 许可证 (License)

本项目采用 MIT 许可证。
