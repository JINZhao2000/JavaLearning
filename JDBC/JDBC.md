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

- 如果在指定了相关的 Blob 类型以后，还报错：xxx too large，那么在 mysql 的安装目录下，找 my.ini 文件加上如下的配置参数：max_allowed_packet=16M，默认为1M。同时注意：修改 my.ini 文件之后，需要重新启动 mysql 服务器

### 4.2 向数据表中插入大数据类型

```java
void setBlob(int parameterIndex, InputStream inputStream);
```

### 4.3 从数据表读取大数据类型

```java
java.io.InputStream getBinaryStream () throws SQLException;
```

## 5. 批量写入数据

使用 Batch 

默认不开启，在连接的 url 后面加上参数可开启

```properties
?rewriteBatchedStatements=true
```

使用 Batch

```java
for (int i = 0; i < 2000; i++) {
	statement.setObject(1,"name_"+i);
	statement.addBatch();
	if((i+1)%500==0){
		statement.executeBatch();
	statement.clearBatch();
	}
}
```

继续优化

```java
connection.setAutoCommit(false);
connection.commit();
```

## 6. 数据库事务

### 6.1 数据库事务介绍

- 事务：一组逻辑操作单元，实数据从一种转态转变为另一种状态
- 事务处理（事务操作）：保证所有事务都作为一个工作单元来执行，即使出现了故障，都不能改变这种执行方式，当一个事务中执行多个操作时，要么所有事物都被提交（committed），那么这些修改就永久地保存下来，要么数据库管理系统将放弃所有的修改，整个事务回滚（rollback）到最初的状态
- 为确保数据库中数据的一致性，数据的操纵应当是离散或者成组的逻辑单元：当它全部完成时，数据的一致性可以保持，而当这个单元的一部分操作失败，整个事务应全部视为错误，所有从起始点以后的操作应全部回退到原始状态

### 6.2 JDBC 事务处理

- 数据一旦提交，就不可以回滚
- 数据什么时候意味着提交
  - 当一个连接对象被创建时，默认情况下是自动提交事务：每一次执行 SQL 语句的时，如果执行成功，就会向数据库自动提交数据，而不能回滚
  - DDL，DML 默认自动提交可以修改
  - 关闭数据库连接，数据就会自动提交，如果多个操作，每个操作使用的时自己单独的连接，则无法保证事务，即同一个事务的多个操作必须在同一个连接下
- JDBC 程序中为了让多个 SQL 语句作为一个事务执行：

### 6.3 事务的 ACID

- 原子性 Atomicity

  原子性指事务时一个不可分割的工作单位，事务中的操作要么都发生，要么都不发生

- 一致性 Consistency

  事务必须使数据库从一个一致性状态变换到另一个一致性状态

- 隔离性 Isolation

  事物的隔离性是指一个事务的执行不能被其他事务干扰，即一个事务内部的操作及使用的数据对并发的其他事务是隔离的，并发执行的各个事务之间不能相互干扰

- 持久性 Durability

  持久性是指一个事务一旦提交，它对数据库中的数据改变就是永久性的，接下来的其他操作和数据库故障不应该对其有任何影响

### 6.4 数据库并发问题

- 对于同时运行的多个事务，当这些事务访问数据库中相同的数据时，如果没有采取必要的隔离机制，就会导致各种并发问题
  - 脏读：对于两个事务 T1，T2，T1 读取了已经被 T2 更行但是还没有被提交的字段，之后，若 T2 回滚，T1 读取的内容就是临时且无效的
  - 不可重复读：对于两个事务 T1，T2，T1 读取了一个字段，然后 T2 更新了该字段，之后 T1 再次读取同一个字段，值就不同了
  - 幻读：对于两个事务T1，T2，T1 从一个表中读取了一个字段，然后 T2 在该表中插入了一些新行，之后，如果 T1 再次读取同一个表，就会多出几行
- 数据库事务的隔离性：数据库系统必须具有隔离并发运行各个事务的能力，使他们不会互相影响，避免发生各种并发问题
- 一个事务与其它事务隔离的程度称为隔离级别，数据库规定了多种事务隔离级别，不同隔离级别对应于不同的干扰程度，隔离级别越高，数据一致性就越好，但并发性越弱

### 6.5 4 种隔离级别

| 隔离级别                      | 描述                                                         |
| ----------------------------- | ------------------------------------------------------------ |
| READ UNCOMMITTED 读未提交数据 | 允许事务读取未被其他事务提交的变更，问题：脏读，不可重复读，幻读 |
| READ COMMITTED 读已提交数据   | 只允许事务读取已经被其他事务提交的变更，可以避免脏读，问题：不可重复读，幻读 |
| REPEATABLE READ 可重复读      | 确保事务可以多次从一个字段中读取相同的值，在这个事务持续期间，禁止其他事务对这个字段进行更新，可以避免脏读和不可重复读，问题：幻读 |
| SERIALIZABLE 串行化           | 确保事务可以从一个表中读取相同的行，在这个事务持续期间，禁止其他事务对该表执行插入，更新和删除操作，所有并发问题都可以避免，但性能十分低下 |

- Oracle 支持两种隔离级别： READ COMMITTED ，SERIALIZABLE，默认隔离级别为 READ COMMITTED
- MySQL 支持四种隔离级别，默认隔离级别为 REPEATABLE READ

### 6.6 在 MySQL 中设置事务隔离级别

- 每启动一个 mysql 程序，就会获取一个单独的数据库连接，每个数据库连接都有一个全局变量 `@@tx_isolation` 表示当前的事务隔离级别

- 查看当前的隔离级别

  ```mysql
  select @@tx_isolation; // mysql 5.0
  select @@transaction_isolation // mysql 8.0+
  ```

- 设置当前 mysql 连接的隔离级别

  ```mysql
  set transaction isolation level read committed;
  ```

- 设置数据库系统的全局的隔离级别

  ```mysql
  set global transaction isolation level read committed;
  ```

- 补充操作

  - 创建 mysql 数据库用户

    ```mysql
    create user xxx identified by 'xxx';
    ```

  - 授予权限

    ```mysql
    grant privileges 
    ```

    

## 7. DAO 及其实现类



## 8. 数据库连接池

## 9. Apache-DBUtils 实现 CRUD 操作

## 总结

