

### :fire: 懒得写代码

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

这是生成 `VO` 的实现代码

```java
DataModel dataModel = dBScriptRunner.excute(sql).asDataModel();
logger.info("根据sql生成 VO 元数据：{}", dataModel.toJSON());
// 待写入的文件位置
String file = LazyKit.javaAbsoluteFilePath(voName);
logger.info("根据sql生成 VO【{}】，文件【{}】", voName, file);
// 根据 freemark 生成文件
dataModel.addData("entityName", voName).write("vo.ftl", file);
// dataModel 是自己定义的数据模型，可以以 JSON 格式输出一下 ，方便在模版中使用
```

#### 启动

运行 `Bootstrap` 试试；只有几个类，熟悉 spring 的话，想怎么用就怎么用。

#### 扩展

新增 `Generator` 中新增方法即可，抛砖引玉，谢谢支持

