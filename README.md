# Flow

极简版本的 BaaS 服务,纯粹练手项目

## 计划

目前进度: 1%

- 以 Vert.x3 为服务器基础架构
- 支持 Fat Jar 和 Docker 部署
- 不支持事务时可以用同样方式访问 MongoDB, MySQL, H2
- 文件存储支持 Local File System, GridFS, S3, HDFS
- 提供 Android 和 iOS 的 Todo List 应用示例
- 提供基于 Spark Core 离线分析
- 提供基于 Spark Streaming 实时分析
- 前端以 React + Jade 进行渲染
- 提供资源自动创建工具

## 项目结构

- example 示例代码
- flow-sdk   基于 JDK 7, SDK 接口定义
- flow-client-sdk 基于 JDK 7,客户端 SDK,支持 Java 和 Android 程序
- flow-server-sdk 基于 JDK 7,服务端 SDK,支持 Java
- flow-compiler Apt 编译器,使用 Kotlin 编写
- flow-core  基于 JDK 7,核心库,支持 Java 和 Android
- flow-data-service 基于 JDK 8, 定义数据和文件的访问接口
- flow-mongo-service 基于 JDK 8,实现对 MongoDB 的数据访问
- flow-mysql-service 基于 JDK 8,实现对 MySQL 的数据访问
- flow-server 基于 JDK 8,应用服务器
- flow-analytics 数据分析模块,使用 Scala 编写

