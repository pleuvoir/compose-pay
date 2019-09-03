

### 代码生成器

使用  `freemarker` 来生成代码，默认生成的文件位置在  `target/code` 目录下

### 使用

#### 修改配置文件

```xml
<bean class="io.github.pleuvoir.sql.MetaDataConfiguration">
	<!-- 模版位置， 此为存放 ftl 文件的位置 -->
	<property name="ftlLocation" value="classpath:templates" />
	<!-- 数据源 -->
	<property name="dataSourceConfig">
		<bean class="io.github.pleuvoir.sql.bean.DataSourceConfig">
			<property name="driverClass" value="oracle.jdbc.OracleDriver" />
			<property name="url" value="jdbc:oracle:thin:@127.0.0.1:1521:orcl" />
			<property name="username" value="scott" />
			<property name="password" value="tiger" />
		</bean>
	</property>
```

### Example

一键生成增删改差

```java
generator.CRUD("p_pay_product");
```

