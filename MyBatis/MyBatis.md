# MyBatis

[MyBatis 官网](https://mybatis.org/mybatis-3/zh/index.html)

## 1. 简介

- 什么是 MyBatis

  - 持久层框架
  - 避免几乎所有的 JDBC 代码
  - 用 xml 或注解来配置和映射原生类型，接口和 POJO

- MyBatis 依赖

  github : https://github.com/mybatis/mybatis-3

  ```xml
  <dependencies>
      <dependency>
        <groupId>org.mybatis</groupId>
          <artifactId>mybatis</artifactId>
      </dependency>
  </dependencies>
  ```
  
- 优点

  - 简单易学
  - 灵活
  - sql 和代码的分离，提高了可维护性
  - 提供了映射便签，支持对象与数据库的 orm 字段关系映射
  - 提供对象关系映射标签，支持对象关系组建维护
  - 提供 xml 标签，支持编写动态 sql

## 2. 第一个 MyBatis 程序

- 搭建环境

  ```mysql
  CREATE TABLE mb_user (
    uid INT PRIMARY KEY AUTO_INCREMENT,
    uname VARCHAR(20) DEFAULT NULL,
    pwd VARCHAR(15) DEFAULT NULL
  );
  INSERT INTO mb_user(uname, pwd) VALUES('USER1','123456');
  INSERT INTO mb_user(uname, pwd) VALUES('USER2','123456');
  INSERT INTO mb_user(uname, pwd) VALUES('USER3','123456');
  INSERT INTO mb_user(uname, pwd) VALUES('USER4','123456');
  ```

  

