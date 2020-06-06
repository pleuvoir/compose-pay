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
| ` compose-pay-channel-service` |  三方通道服务  |      |      |
| `compose-pay-gateway-service`  |    支付网关    |      |      |
|    `compose-pay-job-admin`     | 定时任务控制台 |      |      |
|       `compose-pay-job`        |    定时任务    |      |      |
|     `compose-pay-manager`      |  运营管理平台  |      |      |
|    `compose-pay-migration`     |  系统迁移脚本  |      |      |
|     `compose-pay-open-api`     | 支付 HTTP 服务 |      |      |



## 技术栈
springmvc、springboot、dubbo、zookeeper、redis、mybatis-plus、rabbitmq、mysql、sharding-jdbc

## 开源协议
[Apache License](LICENSE)

