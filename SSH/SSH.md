# SSH

## 1. jar 整合

- maven properties

  ```xml
  <properties>
  	<maven.compiler.source>1.11</maven.compiler.source>
      <maven.compiler.target>1.11</maven.compiler.target>
  	<junit>4.12</junit>
      <struts2>2.5.25</struts2>
      <asm>3.3</asm>
      <spring>5.2.6.RELEASE</spring>
      <mysql>8.0.22</mysql>
  	<c3p0>0.9.5.5</c3p0>
      <hibernate>5.4.23.Final</hibernate>
      <log4j>2.12.1</log4j>
  </properties>
  ```

- Hibernate

  slf4j -> 日志框架 -> 过滤jar -> 具体日志 log4j slf4j-log4j\*.\*.jar

  jta -> Java transaction api 规范

  dom4j -> 解析 xml

  commons-collections -> Java 集合增强包

  antlr -> 解析HQL 语句

  jpa 规范 -> 注解开发

  ehcache -> 二级缓存

  ```xml
  <dependency>
  	<groupId>org.hibernate</groupId>
  	<artifactId>hibernate-entitymanager</artifactId>
  	<version>${hibernate}</version>
  </dependency>
  <dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-ehcache</artifactId>
      <version>${hibernate}</version>
  </dependency>
  <dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-c3p0</artifactId>
      <version>${hibernate}</version>
  </dependency>
  ```

- Spring

  spring 4+1 + aop 2+2

  ```xml
  <dependency>
  	<groupId>org.springframework</groupId>
  	<artifactId>spring-context</artifactId>
  	<version>5.2.6.RELEASE</version>
  </dependency>
  <!-- context = core + beans + context + expression + aop-->
  <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjweaver</artifactId>
      <version>1.9.6</version>
  </dependency>
  ```

  db jdbc+tx

  ```xml
  <dependency>
  	<groupId>org.springframework</groupId>
  	<artifactId>spring-jdbc</artifactId>
  	<version>${spring}</version>
  </dependency>
  <dependency>
  	<groupId>org.springframework</groupId>
  	<artifactId>spring-tx</artifactId>
  	<version>${spring}</version>
  </dependency>
  ```

  web + test

  ```xml
  <dependency>
  	<groupId>org.springframework</groupId>
  	<artifactId>spring-web</artifactId>
  	<version>${spring}</version>
  </dependency>
  <dependency>
  	<groupId>org.springframework</groupId>
  	<artifactId>spring-test</artifactId>
  	<version>${spring}</version>
  	<scope>test</scope>
  </dependency>
  ```

  mysql c3p0

  ```xml
  <dependency>
  	<groupId>mysql</groupId>
  	<artifactId>mysql-connector-java</artifactId>
  	<version>${mysql}</version>
  </dependency>
  <dependency>
  	<groupId>com.mchange</groupId>
  	<artifactId>c3p0</artifactId>
  	<version>${c3p0}</version>
  </dependency>
  ```

  hibernate

  ```xml
  <dependency>
  	<groupId>org.springframework</groupId>
  	<artifactId>spring-orm</artifactId>
  	<version>${spring}</version>
  </dependency>
  ```

- Struts2 - Webwork

  asm -> 字节码增强框架

  commons-* -> Apache 工具类

  - logging 简洁日志
  - lang3 java.lang 增强包
  - io java.io 增强包
  - fileupload 文件上传

  freemarker -> 模板技术 扩展名：*.ftl

  javassist -> 字节码增强器

  log4j -> Apache 日志文件 /src/log4j.properties

  值栈 -> ognl 表达式

  Webwork -> xwork-core （当前版本已整合）

  ```xml
  <dependency>
  	<groupId>org.apache.struts</groupId>
  	<artifactId>struts2-core</artifactId>
  	<version>${struts2}</version>
  </dependency>
  <dependency>
  	<groupId>asm</groupId>
  	<artifactId>asm-commons</artifactId>
  	<version>${asm}</version>
  </dependency>
  <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-core</artifactId>
      <version>${log4j}</version>
  </dependency>
  <dependency>
  	<groupId>org.apache.struts</groupId>
  	<artifactId>struts2-spring-plugin</artifactId>
  	<version>${struts2}</version>
  	<exclusions>
  		<exclusion>
  			<groupId>org.springframework</groupId>
  			<artifactId>spring-core</artifactId>
  		</exclusion>
  		<exclusion>
  			<groupId>org.springframework</groupId>
  			<artifactId>spring-beans</artifactId>
  		</exclusion>
  	</exclusions>
  </dependency>
  ```
  
  - 模板技术：页面静态化
    - freemarker 扩展名：*.ftl
    - velocity 扩展名：*.vm
  
- 整合包

  - spring-hibernate : spring-orm
  - struts2-spring : struts-spring-plugin

## 2. Spring 整合 Hibernate - hibernate.cfg.xml

- 创建表

  ```mysql
  CREATE TABLE t_user(
    uid INT PRIMARY KEY AUTO_INCREMENT,
    uname VARCHAR(50),
    pwd VARCHAR(32),
    age INT
  )
  ```

- JavaBean

  ```java
  public class User {
      private int uid;
      private String uname;
      private String pwd;
      private int age;
  
      public User() {}
  
      @Override
      public String toString() {
          return "User{" +
                  "uid=" + uid +
                  ", uname='" + uname + '\'' +
                  ", pwd='" + pwd + '\'' +
                  ", age=" + age +
                  '}';
      }
  
      public int getUid() {
          return uid;
      }
  
      public void setUid(int uid) {
          this.uid = uid;
      }
  
      public String getUname() {
          return uname;
      }
  
      public void setUname(String uname) {
          this.uname = uname;
      }
  
      public String getPwd() {
          return pwd;
      }
  
      public void setPwd(String pwd) {
          this.pwd = pwd;
      }
  
      public int getAge() {
          return age;
      }
  
      public void setAge(int age) {
          this.age = age;
      }
  }
  ```

- hbm 映射文件

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE hibernate-mapping PUBLIC
          "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
          "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">
  <hibernate-mapping>
      <class name="com.ayy.bean.User" table="t_user">
          <id name="uid">
              <generator class="native"/>
          </id>
          <property name="uname"/>
          <property name="pwd"/>
          <property name="age"/>
      </class>
  </hibernate-mapping>
  ```

- dao

  - Spring 提供了 HibernateTemplate 用于操作 POJO 对象，类似 Hibernate Session 对象

  ```java
  public class UserDaoImpl implements UserDao {
      private HibernateTemplate template;
  
      public void setTemplate(HibernateTemplate template) {
          this.template = template;
      }
  
      @Override
      public void save(User user) {
          template.save(user);
      }
  }
  ```

- service

  ```java
  public class UserServiceImpl implements UserService {
      private UserDao dao;
  
      public void setDao(UserDao dao) {
          this.dao = dao;
      }
  
      @Override
      public void register(User user) {
          dao.save(user);
      }
  }
  ```

- HIbernate cfg 文件

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE hibernate-configuration PUBLIC
          "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
          "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
  <hibernate-configuration>
      <session-factory>
          <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
          <property name="hibernate.connection.url">jdbc:mysql://rm-4xo63l909kdrexz8kio.mysql.germany.rds.aliyuncs.com:3306/jdbc_test?rewriteBatchedStatements=true</property>
          <property name="hibernate.connection.username">jdbc</property>
          <property name="hibernate.connection.password">JDBCjdbc000</property>
  
          <property name="hibernate.dialect">org.hibernate.dialect.MySQL8Dialect</property>
  
          <property name="hibernate.show_sql">true</property>
          <property name="hibernate.format_sql">true</property>
  
          <property name="hibernate.hbm2ddl.auto">update</property>
  
  <!--        <property name="hibernate.current_session_context_class">org.springframework.orm.hibernate5.SpringSessionContext</property>-->
          
          <!-- 不加这个会报错 Cannot unwrap to requested type [javax.sql.DataSource] -->
  		<property name="hibernate.connection.provider_class">org.hibernate.c3p0.internal.C3P0ConnectionProvider</property>
          
          <mapping resource="com/ayy/bean/User.hbm.xml"/>
      </session-factory>
  </hibernate-configuration>
  ```

- Spring 配置

  命名空间

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:aop="http://www.springframework.org/schema/aop"
         xmlns:tx="http://www.springframework.org/schema/tx"
         xmlns:context="http://www.springframework.org/schema/context"
         xsi:schemaLocation="
              http://www.springframework.org/schema/beans
              http://www.springframework.org/schema/beans/spring-beans.xsd
              http://www.springframework.org/schema/context
              http://www.springframework.org/schema/context/spring-context.xsd
              http://www.springframework.org/schema/aop
              http://www.springframework.org/schema/aop/spring-aop.xsd
              http://www.springframework.org/schema/tx
              http://www.springframework.org/schema/tx/spring-tx.xsd">  
  </beans>
  ```

  加载 hibernate.cfg.xml

  ```xml
  <bean id="sessionFactory" class="org.springframework.orm.hibernate5.LocalSessionFactoryBean">
      <property name="configLocation" value="classpath:hibernate.cfg.xml"/>
  </bean>
  ```

  注入模板

  ```xml
  <bean id="hibernateTemplate" class="org.springframework.orm.hibernate5.HibernateTemplate">
      <property name="sessionFactory" ref="sessionFactory"/>
  </bean>
  ```

  dao

  ```xml
  <bean class="com.ayy.dao.impl.UserDaoImpl">
      <property name="template" ref="hibernateTemplate"/>
  </bean>
  ```

  service

  ```xml
  <bean id="userService" class="com.ayy.service.impl.UserServiceImpl">
      <property name="dao" ref="userDao"/>
  </bean>
  ```

  配置事务

  - 事务管理器

    ```xml
    <bean id="transactionManager" class="org.springframework.orm.hibernate5.HibernateTransactionManager">
        <property name="sessionFactory" ref="sessionFactory"/>
    </bean>
    ```

  - 事务

    ```xml
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="register"/>
        </tx:attributes>
    </tx:advice>
    ```

  - AOP

    ```xml
    <aop:config>
        <aop:pointcut id="registerPointcut" expression="execution(* com.ayy.service..*.*(..))"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="registerPointcut"/>
    </aop:config>
    ```

  测试

  ```java
  @RunWith(SpringJUnit4ClassRunner.class)
  @ContextConfiguration(locations = {"classpath:applicationContext.xml"})
  public class TestApp {
      @Autowired
      private UserService userService;
  
      @Test
      public void demo01(){
          User user = new User();
          user.setUname("USR1");
          user.setPwd("123456");
          user.setAge(18);
  
          userService.register(user);
      }
  }
  ```

## 3. Spring 整合 Hibernate - 无 hibernate.cfg.xml

- 删除 hibernate.cfg.xml

  - 配置数据源

    ```xml
    <context:property-placeholder location="classpath:jdbc.properties"/>
    
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="${jdbc.driverClass}"/>
        <property name="jdbcUrl" value="${jdbc.jdbcUrl}"/>
        <property name="user" value="${jdbc.user}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>
    ```

  - 配置 LocalSessionFactoryBean

    ```xml
    <bean id="sessionFactory" class="org.springframework.orm.hibernate5.LocalSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="hibernateProperties">
            <props>
                <prop key="hibernate.show_sql">true</prop>
                <prop key="hibernate.format_sql">true</prop>
                <prop key="hibernate.hbm2ddl.auto">update</prop>
                <prop key="hibernate.dialect">org.hibernate.dialect.MySQL8Dialect</prop>
            </props>
        </property>
        <!--
    		mappingResources          : 从 src 下加载指定的映射文件，不支持通配符
    		    <property name="mappingResources" value="com/ayy/bean/User.hbm.xml"/>
    		mappingLocations          : 确定映射文件位置，需要 classpath:，支持通配符
    			<property name="mappingLocations" value="classpath:com/ayy/*/*.hbm.xml"/>
    		mappingJarLocations       : 从 jar 包中获得映射文件
    		mappingDirectoryLocations : 加载指定目录下的配置文件
    			<property name="mappingDirectoryLocations" value="com/ayy/bean/"/>
        -->
        <property name="mappingResources" value="classpath:com/ayy/bean/User.hbm.xml"/>
    </bean>
    ```

- 修改 DaoSupport

  - 类

    ```java
    public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
        @Override
        public void save(User user) {
            this.getHibernateTemplate().save(user);
        }
    }
    ```

  - Spring 配置

    ```xml
    <bean id="userDao" class="com.ayy.dao.impl.UserDaoImpl">
        <property name="sessionFactory" ref="sessionFactory"/>
    </bean>
    ```

## 4. Struts 整合 Spring - Spring 创建 Action

- 编写 Action 类并且配置给 Spring

  - 类

    ```java
    public class UserAction extends ActionSupport implements ModelDriven<User> {
        private User user = new User();
    
        @Override
        public User getModel() {
            return user;
        }
    
        private UserService service;
    
        public void setService(UserService service) {
            this.service = service;
        }
    
        public String register(){
            service.register(user);
            return Action.SUCCESS;
        }
    }
    ```

  - Spring

    ```xml
    <bean id="userAction" class="com.ayy.action.UserAction" scope="prototype">
        <property name="service" ref="userService"/>
    </bean>
    ```

- 编写 struts.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE struts PUBLIC
          "-//Apache Software Foundation//DTD Struts Configuration 2.5//EN"
          "http://struts.apache.org/dtds/struts-2.5.dtd">
  
  <struts>
      <constant name="struts.devMode" value="true"/>
  
      <package name="ayy" namespace="/" extends="struts-default">
          <!-- getBean("userAction") -->
          <action name="User_*" class="userAction" method="{1}">
              <result>/success.jsp</result>
              <allowed-methods>register</allowed-methods>
          </action>
      </package>
  </struts>
  ```

- 表单的 jsp 页面

  ```jsp
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <html>
  <head>
      <title>User</title>
  </head>
  <body>
  <form action="./User_register.action" method="post">
      Username:<input type="text" name="uname"><br>
      Password:<input type="password" name="pwd"><br>
      Age:<input type="number" name="age"><br>
      <input type="submit" value="Register">
  </form>
  </body>
  </html>
  ```

- web.xml 配置

  - contextConfigLocation
  - ContextLoaderListener
  - StrutsPrepareAndExecuteFilter

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <web-app version="2.5"
           xmlns="http://java.sun.com/xml/ns/javaee"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
                               http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd">
      <display-name/>
      <filter>
          <filter-name>struts2</filter-name>
          <filter-class>org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter</filter-class>
      </filter>
      <filter-mapping>
          <filter-name>struts2</filter-name>
          <url-pattern>*.action</url-pattern>
      </filter-mapping>
  
      <jsp-config>
          <taglib>
              <taglib-uri>/struts-tags</taglib-uri>
              <taglib-location>/WEB-INF/struts-tags.tld</taglib-location>
          </taglib>
      </jsp-config>
  
      <context-param>
          <param-name>contextConfigLocation</param-name>
          <param-value>classpath:applicationContext.xml</param-value>
      </context-param>
      <listener>
          <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
      </listener>
  
      <welcome-file-list>
          <welcome-file>index.jsp</welcome-file>
      </welcome-file-list>
  </web-app>
  ```

## 5. Struts 整合 Spring - Struts 创建 Action

- 删除 Spring Action 的配置

- Struts 的 Action class 为类全限定名

- __Action 类中的 Service 类的属性名必须和 Spring 配置中的类对应的 Id 完全相同__ （因为会调用 setter 方法自动注入）

- Struts 配置文件

  - default.properties 常量配置文件 常量的使用，后面覆盖前面的

    ```xml
    # action 对象实例由 spring 创建，默认不生效
    # struts.objectFactory = spring
    
    # 如果使用 spring 将自动注入，默认按照名称注入，需要上面生效
    ### valid values are: name, type, auto, and constructor (name is the default)
    struts.objectFactory.spring.autoWire = name
    
    # 此配置文件确定了按照名称自动注入
    ```

  - struts-default.xml 默认核心配置文件

  - struts-plugin.xml 插件配置文件

    ```xml
    <!-- 此配置说明了 struts 的 action 由 spring 创建 -->
    <constant name="struts.objectFactory" value="spring" />
    ```

  - struts.xml 自定义核心配置文件

## 6. 总结

1. dao

   HibernateDaoSupport（底层使用 HibernateTemplate）

2. struts 默认按照名称注入

3. spring 配置

   1. 配置 c3p0 ComboPooledDataSource

   2. 配置 SessionFactory LocalSessionFactory

      dataSource

      hibernateProperties

      mappingLocations

   3. dao 需要注入 SessionFactory

      事务 HibernateTransactionFactory，需要注入 SessionFactory

      \<tx:advice>

      \<aop:config>

      