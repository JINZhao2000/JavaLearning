# SSM

## 1. 环境搭建

- 数据库

  ```mysql
  DROP TABLE IF EXISTS books;
  CREATE TABLE books(
      book_id INT PRIMARY KEY AUTO_INCREMENT,
      book_name VARCHAR(100) NOT NULL,
      book_stock INT NOT NULL,
      book_details VARCHAR(100)
  );
  INSERT INTO books(book_name, book_stock, book_details)
  VALUES ('Java',5,'JavaBase'),('Php',10,'PhpBase'),('MySQL',4,'MySQL Advanced'),('Linux',6,'Linux Socket');
  ```

- maven 依赖

  ```xml
  <dependencies>
      <!-- junit -->
      <dependency>
          <groupId>junit</groupId>
          <artifactId>junit</artifactId>
          <version>4.12</version>
          <scope>test</scope>
      </dependency>
      <!-- DB -->
      <dependency>
          <groupId>mysql</groupId>
          <artifactId>mysql-connector-java</artifactId>
          <version>8.0.22</version>
      </dependency>
      <dependency>
          <groupId>com.mchange</groupId>
          <artifactId>c3p0</artifactId>
          <version>0.9.5.5</version>
      </dependency>
      <!-- servlet -->
      <dependency>
          <groupId>org.apache.tomcat</groupId>
          <artifactId>tomcat-servlet-api</artifactId>
          <version>9.0.38</version>
          <scope>provided</scope>
      </dependency>
      <dependency>
          <groupId>org.apache.tomcat</groupId>
          <artifactId>tomcat-jsp-api</artifactId>
          <version>9.0.38</version>
      </dependency>
      <dependency>
          <groupId>javax.servlet.jsp.jstl</groupId>
          <artifactId>jstl-api</artifactId>
          <version>1.2</version>
          <exclusions>
              <exclusion>
                  <groupId>javax.servlet</groupId>
                  <artifactId>servlet-api</artifactId>
              </exclusion>
              <exclusion>
                  <groupId>javax.servlet.jsp</groupId>
                  <artifactId>jsp-api</artifactId>
              </exclusion>
          </exclusions>
      </dependency>
      <dependency>
          <groupId>taglibs</groupId>
          <artifactId>standard</artifactId>
          <version>1.1.2</version>
      </dependency>
      <!-- catalina.properties 中配置
   		tomcat.util.scan.StandardJarScanFilter.jarsToSkip=\
  	-->
      <!-- MyBatis -->
      <dependency>
          <groupId>org.mybatis</groupId>
          <artifactId>mybatis</artifactId>
          <version>3.5.6</version>
      </dependency>
      <dependency>
          <groupId>org.mybatis</groupId>
          <artifactId>mybatis-spring</artifactId>
          <version>2.0.6</version>
      </dependency>
      <!-- Spring SpringMVC -->
      <dependency>
          <groupId>org.springframework</groupId>
          <artifactId>spring-webmvc</artifactId>
          <version>5.3.4</version>
      </dependency>
      <dependency>
          <groupId>org.springframework</groupId>
          <artifactId>spring-jdbc</artifactId>
          <version>5.3.4</version>
      </dependency>
  </dependencies>
  ```

- 构建配置

  ```xml
  <build>
      <resources>
          <resource>
              <directory>src/main/resources</directory>
          </resource>
          <resource>
              <directory>src/main/java</directory>
              <includes>
                  <include>**/*.xml</include>
                  <include>**/*.properties</include>
              </includes>
              <filtering>false</filtering>
          </resource>
      </resources>
  </build>
  ```

## 2. 配置文件

- MyBatis

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration
          PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
      <settings>
          <setting name="mapUnderscoreToCamelCase" value="true"/>
      </settings>
      <typeAliases>
          <typeAlias type="com.ayy.bean.Books" alias="books"/>
      </typeAliases>
      <mappers>
          <mapper class="com.ayy.dao.BookMapper"/>
      </mappers>
  </configuration>
  ```

- Spring dao

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:context="http://www.springframework.org/schema/context"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/context
          http://www.springframework.org/schema/context/spring-context.xsd ">
  
      <context:property-placeholder location="classpath:aliyun.properties"/>
  
      <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
          <property name="driverClass" value="${driverClass}"/>
          <property name="jdbcUrl" value="${url}"/>
          <property name="user" value="${user}"/>
          <property name="password" value="${password}"/>
      </bean>
  
      <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
          <property name="dataSource" ref="dataSource"/>
          <property name="configLocation" value="classpath:mybatis-config.xml"/>
      </bean>
  
      <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
          <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
          <property name="basePackage" value="com.ayy.dao"/>
      </bean>
  </beans>
  ```

- Spring service

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:context="http://www.springframework.org/schema/context"
         xmlns:tx="http://www.springframework.org/schema/tx"
         xmlns:aop="http://www.springframework.org/schema/aop"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/context
          http://www.springframework.org/schema/context/spring-context.xsd
          http://www.springframework.org/schema/tx
          http://www.springframework.org/schema/tx/spring-tx.xsd
          http://www.springframework.org/schema/aop
          http://www.springframework.org/schema/aop/spring-aop.xsd">
  
      <context:component-scan base-package="com.ayy.service"/>
  
      <bean id="bookServiceImpl" class="com.ayy.service.impl.BookServiceImpl">
          <property name="mapper" ref="bookMapper"/>
      </bean>
  
      <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
          <property name="dataSource" ref="dataSource"/>
      </bean>
  
      <tx:advice id="txAdvice" transaction-manager="transactionManager">
          <tx:attributes>
              <tx:method name="*"/>
          </tx:attributes>
      </tx:advice>
  
      <aop:config>
          <aop:pointcut id="service" expression="execution(* com.ayy.service..*.*(..))"/>
          <aop:advisor advice-ref="txAdvice" pointcut-ref="service"/>
      </aop:config>
  </beans>
  ```

- Spring mvc

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:context="http://www.springframework.org/schema/context"
         xmlns:mvc="http://www.springframework.org/schema/mvc"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/mvc
          http://www.springframework.org/schema/mvc/spring-mvc.xsd
          http://www.springframework.org/schema/context
          http://www.springframework.org/schema/context/spring-context.xsd">
  
      <context:component-scan base-package="com.ayy.controller"/>
  
      <mvc:annotation-driven/>
      <mvc:default-servlet-handler/>
  
      <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
          <property name="prefix" value="/WEB=INF/jsp/"/>
          <property name="suffix" value=".jsp"/>
      </bean>
  </beans>
  ```

- ApplicationContext

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
      <import resource="classpath:spring-dao.xml"/>
      <import resource="classpath:spring-service.xml"/>
      <import resource="classpath:spring-mvc.xml"/>
  </beans>
  ```

  

