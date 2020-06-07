# compose-pay


> doing..

## 介绍
互联网应用中经常要使用支付功能，支付系统做为基础设施，应当是高可用、灵活可配置且不与业务系统耦合的。基于此，剥离出该系统。

系统主要由三部分构成：
1. 数据库迁移工具
2. 后台管理
3. 后端服务

## 功能

1. 传统支付
2. 聚合码

## 项目结构

|              模块              |      名称      | 简介 | 备注 |
| :----------------------------: | :------------: | :--: | :--: |
|     `compose-pay-open-api`     | 支付 HTTP 服务 | WEB  |      |
| `compose-pay-gateway-service`  |  支付网关服务  | RPC  |      |
| ` compose-pay-channel-service` |  三方通道服务  | RPC  |      |
|   `compose-pay-risk-service`   |    风控服务    | RPC  |      |
|   `compose-pay-router-service`   |    支付路由服务    | RPC  |     TODO  |
|    `compose-pay-job-admin`     | 定时任务控制台 | WEB  |      |
|       `compose-pay-job`        |    定时任务    | WEB  |      |
|     `compose-pay-manager`      |  运营管理平台  | WEB  |      |
|    `compose-pay-migration`     |  系统迁移脚本  |      |      |

## 技术栈

| 			框架 					| 	简介	 |  备注 |
| :----------------------------: | :------------: | :-: |
| [Spring Boot](https://spring.io/projects/spring-boot) | 应用开发 + MVC 框架 ||
| [Dubbo](http://dubbo.apache.org/) | 分布式 RPC 服务框架 |  |
| [MySQL](https://www.mysql.com/cn/) | 数据库服务器 |  |
| [Druid](https://github.com/alibaba/druid) | JDBC 连接池、监控组件 |  |
| [MyBatis](http://www.mybatis.org/mybatis-3/zh/index.html) | 数据持久层框架 |  |
| [MyBatis-Plus](https://mp.baomidou.com/) | Mybatis 增强工具包 |  |
| [Redis](https://redis.io/) | key-value 数据库 |  |
| [Elasticsearch](https://www.elastic.co/cn/) | 分布式搜索引擎 |  |
| [RocketMQ](http://dubbo.apache.org/) | 消息中间件 |  |
| [Seata](https://github.com/seata/seata) | 分布式事务中间件 | 待引入 |
| [XXL-Job](http://www.xuxueli.com/xxl-job/) | 分布式任务调度平台 |  |
| [sharding-jdbc]() | 分库分表中间件 | 待引入|
| [Lombok](https://github.com/rzwitserloot/lombok) | 简化对象封装工具 | |
| [Hibernator-Validator](http://hibernate.org/validator) | 验证框架 | |

## 架构图

### 系统架构图

// TODO

### 业务架构图

// TODO

## 开源协议
[Apache License](LICENSE)

