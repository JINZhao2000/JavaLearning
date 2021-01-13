# JDBC

## 1 JDBC 介绍

### 1.1 Java 中数据储存技术

>JDBC Java Database Connectivity 
>
>JDO Java Data Object
>
>Hibernate, Mybatis

### 1.2 API

java.sql

javax.sql

### 1.3 关系型数据库

- Oracle
- MySQL
- SqlServer
- DB2

### 1.4 JDBC 编写顺序

- 开始
- 导入 java.sql
- 导入对应数据库提供的驱动 / 建立数据源 ODBC Open Database Connectivity -> SqlServer
- 加载并注册驱动程序
- 创建 Connection 对象
- 创建 Statement 对象
- 执行 SQL 语句
- _使用 ResultSet 对象_
- _关闭 ResultSet 对象_
- 关闭 Statement 对象
- 关闭 Connection 对象
- 结束

## 2. 获取数据库连接

### 2.1 Driver

用于连接数据库

```java
Driver driver = new com.mysql.cj.jdbc.Driver();
```

com.mysql.jdbc.Driver 已过期

### 2.2 URL

jdbc:(子协议):(子名称)

协议 ：JDBC

子协议：数据库驱动程序标识

子名称：定位数据库 - 主机名 + 端口号 + 数据库名

Ex : 

```java 
String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
```

### 2.3 用户名和密码

user / password -> properties

### 2.4 数据库连接方式

第一种

```java
Driver driver = new com.mysql.cj.jdbc.Driver();
String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
Properties info = new Properties();
info.setProperty("user","root");
info.setProperty("password","root");
Connection connection = driver.connect(url,info);
```

第二种

```java
Class<?> classDriver = Class.forName("com.mysql.cj.jdbc.Driver");
Driver driver = (Driver) classDriver.getDeclaredConstructor().newInstance();

String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
Properties info = new Properties();
info.setProperty("user","root");
info.setProperty("password","root");

Connection connection = driver.connect(url,info);
```

第三种

```java
Class<?> classDriver = Class.forName("com.mysql.cj.jdbc.Driver");
Driver driver = (Driver) classDriver.getDeclaredConstructor().newInstance();

DriverManager.registerDriver(driver);
String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
Properties info = new Properties();
info.setProperty("user","root");
info.setProperty("password","root");

Connection connection = DriverManager.getConnection(url,info);
```

第四种

```java
String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
Properties info = new Properties();
info.setProperty("user","root");
info.setProperty("password","root");

// 通过 Driver的静态代码块注册驱动
Class.forName("com.mysql.cj.jdbc.Driver");

Connection connection = DriverManager.getConnection(url, info);
```

第五种

ConnectionTest.java 文件

```java
InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("MySQLConnectionJDBC.properties");

Properties pros = new Properties();
pros.load(is);

String user = pros.getProperty("user");
String password = pros.getProperty("password");
String url = pros.getProperty("url");
String driverClass = pros.getProperty("driverClass");

Class.forName(driverClass);

Connection connection = DriverManager.getConnection(url,user,password);
```

MySQLConnectionJDBC.properties 文件

```properties
user=root
password=root
url=jdbc:mysql://localhost:3306/test?serverTimezone=UTC
driverClass=com.mysql.cj.jdbc.Driver
```

## 3. 使用 PreparedStatement 实现 CRUD 操作

### 3.1 操作和访问数据库

- 数据库连接被用于向服务器发送命令和 SQL 语句，并接受数据库服务器返回的结果，其实数据库连接就是一个 Socket 连接

- 在 java.sql 中有3个接口定义了对数据库调用的不同方式

  - Statement：用于执行静态 SQL 语句并返回他所生成的结果对象
  - PreparedStatement：SQL 语句被预编译并储存在此对象中，可以使用此对象高效得执行该语句
  - CallableStatement：用于执行 SQL 存储过程

  Driver Manager -> Connection -> Statement / PreparedStatement / CallableStatement -> Result

### 3.2 Statement 操作数据表的弊端

- 通过调用 Connection 对象的 createStatement() 方法创建该对象，用于执行静态的 SQL 语句，并且返回执行结果
- Statement 接口中定义了下列方法用于执行 SQL 语句

```java
int executeUpdate(String sql); // 执行更新操作 INSERT, UPDATE, DELETE
ResultSet executeQuery(String sql); // 执行查询操作 SELECT
```

- 但是使用 Statement 造作数据存在弊端：

  - 问题一：存在拼串操作，繁琐
  - 问题二：存在 SQL 注入问题

- SQL 注入是利用某些系统没有对用户输入的数据进行充分检查，而在用户输入数据中注入非法的 SQL 语句段或命令（如：

  ```java 
  @Test
  public void testLogin2(){
  	String username = "1' or ";
      String password = "=1 or '1' = '1";
  	String sql = "SELECT user,password FROM user_table WHERE user='"+username+"'AND password ='"+password+"';";
      User user = get(sql,User.class);
  	assertNotNull(user);
  }
  ```

  ```sql
  SELECT user, password 
  FROM user_table 
  WHERE user='1' or 'AND password = '=1 OR '1' = '1'
  ```

  ），从而利用系统的 SQL 引擎完成恶意违法的行为

- 对于 Java 而言，要防范 SQL 注入，只要用 PreparedStatement 取代 Statement 就可以了

### 3.3 PreparedStatement 的使用

- 封装 Util

  ```java
  import java.io.InputStream;
  import java.sql.Connection;
  import java.sql.DriverManager;
  import java.util.Properties;
  
  public class JDBCUtils {
      public static Connection getConnection(){
          InputStream is = ClassLoader.getSystemClassLoader().
              getResourceAsStream("MySQLConnectionJDBC.properties");
          Properties pros = new Properties();
          try {
              pros.load(is);
              String user = pros.getProperty("user");
              String password = pros.getProperty("password");
              String url = pros.getProperty("url");
              String driverClass = pros.getProperty("driverClass");
              Class.forName(driverClass);
              Connection connection = DriverManager.getConnection(url, user, password);
              return connection;
          } catch (Exception throwables) {
              throwables.printStackTrace();
          }
          return null;
      }
  
      public static void close(AutoCloseable... clos){
          for (AutoCloseable clo:clos) {
              try {
                  if(clo!=null) {
                      clo.close();
                  }
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }
      }
  }
  ```

  

- Java 类型与 SQL 类型的关系映射

  | Java    | SQL  |
  | -------- | -------- |
  | boolean | BIT |
  | byte | TINYINT |
  | short | SMALLINT |
  | int | INTEGER |
  | long | BIGINT |
| String | CHAR, VARVHAR(2),LONGVARCHAR |
  | byte array | BINARY, VAR BINARY |
  | java.sql.Date | DATE |
  | java.sql.Time | TIME |
  | java.sql.Timestamp | TIMESTAMP |
  
- PreparedStatement 增删改操作

  ```java
  public void update(String sql,Object... objects){
  	if (JDBCUtils.getConnection() != null) {
  		try (Connection connection = JDBCUtils.getConnection();
  			 PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
  			int index = 1;
  			for (Object o:objects) {
  				preparedStatement.setObject(index,o);
  				index++;
  			}
  			preparedStatement.execute();
  			System.out.println("Update success");
  		} catch (SQLException throwables) {
  			throwables.printStackTrace();
  		}
  	}
  }
  ```

- PreparedStatement 查操作

  ```java
  public <T> List<T> getInstance (Class<T> tClass, String sql, Object... objects) {
  	Connection connection = null;
  	PreparedStatement preparedStatement = null;
  	ResultSet resultSet = null;
  	try {
  		connection = JDBCUtils.getConnection();
  		if (connection != null) {
  			preparedStatement = connection.prepareStatement(sql);
  			for (int i = 0; i < objects.length; i++) {
  				preparedStatement.setObject(i + 1, objects[i]);
  			}
  			resultSet = preparedStatement.executeQuery();
  			if (resultSet != null) {
  				ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
  				int columnCount = resultSetMetaData.getColumnCount();
  				List<T> listResult = new ArrayList<>();
  				while (resultSet.next()) {
  					T t = tClass.getDeclaredConstructor().newInstance();
  					for (int i = 0; i < columnCount; i++) {
  						Object columnValue = resultSet.getObject(i + 1);
  						String columeLabel = resultSetMetaData.getColumnLabel(i + 1);
  						Field field = tClass.getDeclaredField(columeLabel);
  						field.setAccessible(true);
  						field.set(t, columnValue);
  					}
  					listResult.add(t);
  				}
  				return listResult;
  			}
  		}
  	} catch (Exception e) {
  		e.printStackTrace();
  	} finally {
  		JDBCUtils.close(connection, preparedStatement, resultSet);
  	}
  	return null;
  }
  ```

### 3.4 ResultSet 与 ResultSetMetaData

ResultSet 是结果集，ResultSetMetaData 是结果集的基本信息，包括列数，列名，显示列名等

### 3.5 JDBC API 小结

- 两种思想

  - 面向接口编程 IOP
  - 对象关系映射 ORM
    - 一个数据表对应一个 Java 类
    - 表中的一条记录对应 Java 类的一个对象
    - 表中的一个字段对应 Java 类的一个属性 Field

  >sql 是需要结合列名和表的属性名来写的，注意起别名

- 两种技术

  - JDBC 结果集元数据：ResultSetMetaData
    - 获取列数：getColumnCount()
    - 获取列的别名：getColumnLabel()
  - 通过反射，创建指定类的对象，获取指定的属性并赋值

## 4. 操作 BLOB 类型字段

### 4.1 MySQL BLOB 字段

- MySQL 中，BLOB 字段是一个二进制大型对象，是一个可以储存大量数据的容器，他能容纳不同大小的数据

- 插入 BLOB 类型的数据必须使用 PreparedStatement，因为 BLOB 类型的数据无法使用字符串拼接写的

- MySQL 的四种 BLOB 类型（除了在存储的最大信息量上不同外，他们是等同的）

  | 类型       | 大小     |
  | ---------- | -------- |
  | TinyBlob   | 最大 255 |
  | Blob       | 最大 65K |
  | MediumBlob | 最大 16M |
  | LongBlob   | 最大 4G  |

- 实际使用中根据需要存入的数据大小定义不同的 BLOB 类型

- 需要注意的是：如果存储文件过大，数据库性能会下降

- 如果在指定了相关的 Blob 类型以后，还报错：xxx too large，那么在 mysql 的安装目录下，找 my.ini 文件加上如下的配置参数：max_allowed_packet=16M。同时注意：修改 my.ini 文件之后，需要重新启动 mysql 服务器

### 4.2 向数据表中插入大数据类型

