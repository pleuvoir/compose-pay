

### 代码生成器

使用  `freemarker` 来生成代码，默认生成的文件位置在  `target/code` 目录下

### 使用

#### 修改配置文件

```
datasource.config.driverClass=com.mysql.jdbc.Driver
datasource.config.url=jdbc:mysql://39.105.110.40:3306/compose-pay
datasource.config.username=compose-pay
datasource.config.password=compose-pay

ftlLocation=classpath:templates
```

### Example

一键生成增删改差

```java
generator.CRUD("p_pay_product");
```

