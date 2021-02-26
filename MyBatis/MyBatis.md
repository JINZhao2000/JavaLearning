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

- 实体类

  ```java
  public class User {
      private int uid;
      private String uname;
      private String pwd;
  
      public User() {}
      ...
  }
  ```

- 工具类

  ```java
  public class MyBatisUtils {
      private static SqlSessionFactory sqlSessionFactory;
  
      static {
          try {
              InputStream is = MyBatisUtils.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
              sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
  
      public static SqlSession getSqlSession(){
          return sqlSessionFactory.openSession();
      }
  }
  ```

- Mapper 接口（Dao 层）

  ```java
  public interface UserMapper {
      List<User> getAllUser();
  }
  ```

- Mapper 配置文件

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper
          PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <mapper namespace="com.ayy.dao.UserMapper">
      <select id="getAllUser" resultType="com.ayy.bean.User">
          select * from mb_user
      </select>
  </mapper>
  ```

- MyBatis 配置

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration
          PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
      <environments default="development">
          <environment id="development">
              <transactionManager type="JDBC"/>
              <dataSource type="POOLED">
                  <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                  <property name="url" value="jdbc:mysql://rm-4xo63l909kdrexz8kio.mysql.germany.rds.aliyuncs.com:3306/jdbc_test?rewriteBatchedStatements=true"/>
                  <property name="username" value="jdbc"/>
                  <property name="password" value="JDBCjdbc000"/>
              </dataSource>
          </environment>
      </environments>
      <mappers>
          <mapper resource="com/ayy/dao/UserMapper.xml"/>
      </mappers>
  </configuration>
  ```

- 测试类

  ```java
  public class UserMapperTest {
      private SqlSession sqlSession;
  
      @Before
      public void setUp(){
          sqlSession = MyBatisUtils.getSqlSession();
      }
  
      @After
      public void tearDown(){
          sqlSession.close();
      }
  
      @Test
      public void testGetAllUser(){
          UserMapper mapper = sqlSession.getMapper(UserMapper.class);
          List<User> allUser = mapper.getAllUser();
          allUser.forEach(System.out::println);
      }
  
      @Test
      public void testGetAllUser2(){
          List<User> users = sqlSession.selectList("com.ayy.dao.UserMapper.getAllUser");
          users.forEach(System.out::println);
      }
  }
  ```

## 3. CRUD

- Mapper 文件

  ```xml
  <mapper namespace="com.ayy.dao.UserMapper">
      <select id="getAllUser" resultType="com.ayy.bean.User">
          select * from mb_user
      </select>
  </mapper>
  ```

  - namespace：对应接口名称

- Select

  ```xml
  <!-- List<User> getAllUser(); -->
  <select id="getAllUser" resultType="com.ayy.bean.User">
      select * from mb_user
  </select>
  ```

  - id：方法名
  - resultType：返回类型
  - parameterType：参数类型

  ```xml
  <!-- User getUserById(int id); -->
  <select id="getUserById" parameterType="int" resultType="com.ayy.bean.User">
      select * from mb_user where uid = #{id}
  </select>
  ```

- Insert（注意事务提交）

  ```xml
  <!-- void addUser(User user); -->
  <insert id="addUser" parameterType="com.ayy.bean.User">
      insert into mb_user(uname, pwd) values (#{uname},#{pwd})
  </insert>
  ```

  - 对象中的属性可以直接拿 #{} 号取

- update

  ```xml
  <!-- void updateUser(User user); -->
  <update id="updateUser" parameterType="com.ayy.bean.User">
      update mb_user set uname = #{uname} , pwd = #{pwd} where uid = #{uid}
  </update>
  ```

- delete

  ```xml
  <!-- void deleteUser(int id); -->
  <delete id="deleteUser" parameterType="int">
      delete from mb_user where uid = #{id}
  </delete>
  ```

  