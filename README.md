# xianyuchl1 - 学生二手闲鱼系统

学生二手闲鱼系统，让用户能够正常收藏商品，下单，选择收货地址，查看订单，评价，以及发布商品这一系列操作。

## 功能特性

- ✅ 用户管理：用户信息维护
- ✅ 商品浏览：查看可售商品列表
- ✅ 商品详情：查看商品详细信息、评价
- ✅ 商品发布：用户可以发布二手商品
- ✅ 收藏功能：收藏喜欢的商品
- ✅ 下单购买：选择商品进行购买
- ✅ 地址管理：添加、编辑、删除收货地址
- ✅ 订单管理：查看订单列表和详情，支付、取消订单
- ✅ 商品评价：购买后对商品进行评价

## 技术栈

- **后端框架**: Spring Boot 2.7.14
- **MVC框架**: Spring MVC
- **模板引擎**: Thymeleaf
- **ORM框架**: Spring Data JPA
- **数据库**: H2 (开发环境), MySQL (生产环境)
- **构建工具**: Maven
- **Java版本**: 1.8

## 项目结构

```
xianyuchl1/
├── src/
│   ├── main/
│   │   ├── java/com/xianyu/marketplace/
│   │   │   ├── controller/     # 控制器层
│   │   │   ├── service/        # 服务层
│   │   │   ├── repository/     # 数据访问层
│   │   │   ├── entity/         # 实体类
│   │   │   ├── config/         # 配置类
│   │   │   └── XianyuMarketplaceApplication.java
│   │   └── resources/
│   │       ├── templates/      # Thymeleaf模板
│   │       ├── static/         # 静态资源
│   │       └── application.properties
│   └── test/                   # 测试代码
├── pom.xml
└── README.md
```

## 快速开始

### 环境要求

- JDK 1.8 或更高版本
- Maven 3.6 或更高版本

### 运行步骤

1. 克隆项目
```bash
git clone https://github.com/kkstar11/xianyuchl1.git
cd xianyuchl1
```

2. 编译项目
```bash
mvn clean install
```

3. 运行应用
```bash
mvn spring-boot:run
```

4. 访问应用
打开浏览器访问：http://localhost:8080

5. H2数据库控制台（可选）
访问：http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:xianyudb
- User Name: sa
- Password: (留空)

## 主要页面

- **首页**: `/` - 展示最新商品
- **商品列表**: `/products` - 浏览所有可售商品
- **商品详情**: `/products/{id}` - 查看商品详细信息
- **发布商品**: `/products/publish` - 发布二手商品
- **我的收藏**: `/favorites` - 查看收藏的商品
- **我的订单**: `/orders` - 查看订单列表
- **订单详情**: `/orders/{id}` - 查看订单详情
- **收货地址**: `/addresses` - 管理收货地址
- **商品评价**: `/reviews/create/{orderId}` - 评价订单商品

## 数据库实体

- **User**: 用户信息
- **Product**: 商品信息
- **Order**: 订单信息
- **Address**: 收货地址
- **Favorite**: 收藏记录
- **Review**: 商品评价

## 示例数据

系统启动时会自动初始化一些示例数据：
- 2个示例用户（张三、李四）
- 6个示例商品（MacBook、Java书籍、自行车等）
- 2个示例地址

## 生产环境配置

如需使用MySQL数据库，请修改 `application.properties`:

```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/xianyudb?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

## 许可证

MIT License
