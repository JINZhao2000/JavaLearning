# Servlet

## 1. 服务器

### Tomcat 服务器

配置> CATALINA_HOME/conf/server.xml

```xml
    <Connector port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443" />
```

访问页面为 localhost:8080

>webapps
>
>​	\-\<项目名\>
>
>​		-WEB-INF // 存放项目
>
>​			\-classes // 用于存放 .class
>
>​			\-lib // 用于存放  jar 文件
>
>​			\-web.xml // 配置文件 可从 ROOT 下  WEB-INF 中复制
>
>​		\-exemple.html 网页

访问路径为 localhost:port/\<项目名\>/exemple.html

### Servlet 开发步骤

将 CATALINA_HOME/lib/com.ayy.cookies-api.jar 目录放到环境变量的 classpath 中

编写 com.ayy.cookies

​	实现 javax.com.ayy.cookies.Servlet

​	重写5个方法

​	在核心的 service() 方法中编写输出语句，打印访问结果

```java
import javax.com.ayy.cookies.Servlet;
import javax.com.ayy.cookies.ServletConfig;
import javax.com.ayy.cookies.ServletRequest;
import javax.com.ayy.cookies.ServletResponse;
import javax.com.ayy.cookies.ServletException;
import java.io.IOException;

public class MyServlet implements Servlet{
    public void init(ServletConfig config) throws ServletException{}
    public void service(ServletRequest request, ServletResponse response) 
        throws ServletException, IOException{
        System.out.println("My First Servlet");
    }
    public void destroy(){}
    public ServletConfig getServletConfig(){
        return null;
    }
    public String getServletInfo(){
        return null;
    }
}
```

编译

```shell
javac MyServlet.java
```

将 .class 文件部署到 classes 下

配置 web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
                      http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
  version="4.0"
  metadata-complete="true">
    <com.ayy.cookies>
        <com.ayy.cookies-name>my</com.ayy.cookies-name><!--类代替名-->
        <com.ayy.cookies-class>MyServlet</com.ayy.cookies-class><!--类名-->
    </com.ayy.cookies>
    <com.ayy.cookies-mapping>
        <com.ayy.cookies-name>my</com.ayy.cookies-name><!--映射的类代替名-->
        <url-pattern>/myservlet</url-pattern><!--对应的访问位置 <项目名/访问位置>-->
    </com.ayy.cookies-mapping>
</web-app>
```

导出 war 包

IDEA -> Project Structure -> Artifacts -> Add -> Web Application Archive -> for xxx_war exploded

### HTTP

三次握手，四次挥手

建立连接

发送请求信息

回送响应信息

关闭连接

__Connnection__ 报头控制一次响应的时间

#### HTTP 请求报文

1. 请求行，请求方法/地址 URI 协议/版本
2. 请求头 (Request Header)
3. 空行
4. 请求正文

```http
POST /myweb/userservlet HTTP/1.1
Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,/;q=0.8
Accept-Language:zh-CN,zh;q=0.8,en-GB;q=0.6,en;q=0.4
Connection:Keep-Alive
Host:localhost:8080
User-Agent:Mozilla/5.0 (Windows NT 6.1;Win64;x64) AppleWebKit/537.36(KHTML, like Gecko)
Chrome/59.0.3071.115 Safari/537.36
Accept-Encoding:gzip,deflate,br

username=zhangsan&age=20&add=beijing
```

POST 行为请求行

​	POST 为请求方法

​	/myweb/userservlet 为请求 URL

​	HTTP/1.1 为 HTTP 协议及版本

Accept 行到 Accept-Encoding 行为请求头

username 行为请求正文

#### HTTP响应报文

1. 状态行
2. 响应头 (Reponse Header)
3. 空行
4. 相应正文

```http
HTTP/1.1 200 OK //状态行
Server:Apache-Coyote/1.1
Date: Tue,31 May 2016 02:09:24 GMT
Content-Type:text/html;charset=UTF-8
Connection:keep-alive
Content-length:49

```

响应正文

```html
<!DOCTYPE html>
<html>
    <head>
        <title>title</title>
        <meta charset="utf-8">
    </head>
    <body>
    	content
    </body>
</html>
```

状态码

200 OK

302 Found 临时重定向

403 Forbidden 服务器收到请求，但是拒绝提供服务

404 Not Found 资源不存在

500 Internal Server Error 服务器发生异常，无法响应请求

## 2. Servlet 核心接口和类

实现 Servlet 接口，重写5个方法

​	void init(ServletConfig config)

​	ServletConfig getServletConfig()

​	service(ServletRequest request, ServletResponse response)

​	String getServletInfo()

​	destroy()

继承 GenericServlet

​	提供生命周期方法 init() 和 destroy() 的简单实现，必须重写抽象方法 service()

继承 HttpServlet

​	必须重写以下方法之一

​	doGet，如果 com.ayy.cookies 支持 HTTP GET 请求

​	doPost，用于 HTTP POST 请求

​	doPut，用于 HTTP PUT 请求

​	doDelete，用于 HTTP DELETE 请求

常见问题

404

​	删除 out 文件夹 并重新 build

​	mapping 重复

​	地址配置错误 -> 漏了 /

## 3. Servlet 两种配置方式

__web.xml__

url-pattern 4种方式（优先级）

​	精确匹配	/具体名称	只有 url 路径是具体的名称的时候才会触发 com.ayy.cookies

​	后缀匹配	*.xxx	只要是以 .xxx 结尾的就匹配触发 com.ayy.cookies

​	通配符匹配	/*	匹配所有请求，包含服务器所有资源

​	通配符匹配	/	匹配所有请求，包含服务器的所有资源，不包括 jsp

load-on-startup

1. 元素标记容器是否应该在 web 应用程序启动的时候加载这个 com.ayy.cookies
2. 它的值必须是一个整数，表示 com.ayy.cookies 被加载的先后顺序
3. 如果该元素的值为负数或者没有设置，则容器会当 com.ayy.cookies 被请求时再加载
4. 如果值为正整数或者0时，表示容器在应用启动的时候就加载初始化这个 com.ayy.cookies，值越小，com.ayy.cookies 的优先级越高，就越优先被加载。值相同时，容器会自己选择顺序来加载

__注解 (com.ayy.cookies 3.0+)__

@WebServlet

​	name：com.ayy.cookies 名字 （可选）

​	value：配置 url 路径，可以配置多个

​	urlPatterns：配置 url 路径，不能和 value 同时使用

​	loadOnStartup：配置 com.ayy.cookies 创建时机，如果是0或者正数，启动程序时创建，如果是负数，则访问时创建，数字越小优先级越高

与 web.xml 不冲突

## 4. Servlet 应用

### 4.1 request 对象

在 Servlet 中用来处理客户请求需求用 doGet 或 doPost 方法的 request 对象

两个核心接口 ServletRequest 和 HttpServletRequest

__get 和 post 区别__ 

​	get 请求

​		get 提交的数据会放在 URL 之后，以？分割 URL 和传输数据，参数之间以&相连

​		get 方式明文传递，数据量小，不安全

​		效率高，浏览器默认请求方式为 GET 请求

​		对应的 Servlet 的方法是 doGet

​	post 请求

​		post 方法是把提交的数据放在 HTTP 包的 Body 中

​		密文传递数据，数据量大，安全

​		效率相对没有 GET 高

​		对应的 Servlet 的方法是 doPost

__request 主要方法__ 

| 方法名                                      | 说明                             |
| ------------------------------------------- | -------------------------------- |
| `String getPatamater(String name)`          | 根据表单的组件名称，获取提交数据 |
| `void setCharacterEncoding(String charset)` | 指定每个请求的编码               |

__request 应用__ 

HTML

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <form action="/Servlet02_war_exploded/rs" method="get">
        User&nbsp;name:<input type="text" name="username"><br/>
        Password:<input type="password" name="pwd"><br/>
        <input type="submit" value="Register">
    </form>
</body>
</html>
```

Servlet

```java
@WebServlet(value="/rs")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // get data from request
        String username = req.getParameter("username");
        String pwd = req.getParameter("pwd");
        System.out.println("username: "+username+"\tpwd: "+pwd);
    }
}
```

__get 请求收参问题__ 

产生乱码是因为服务器与客户端沟通编码不一致造成的，因此解决方法是：在客户端与服务端之间设置一个统一的编码，之后就按照此编码进行数据的传输和接收

__get 中文乱码__ 

在 Tomcat 7 及以下版本，客户端 UTF-8 的编码传输数据到服务器端，而服务器端的 request 对象使用的是 ISO8859-1 这个字符编码来接收数据，服务器和客户端沟通的编码不一致因此才会产生中文乱码的

解决办法：在接收到数据后，先获取 request 对象以 ISO8859-1 字符编码接收到的原始数据的字节数组，然后通过字节数组以指定编码构建字符串，解决乱码问题

Tomcat 8 的版本中的 get 方式不会出现乱码了，因为服务器对 url 编码格式可以进行自动转换

```java
String username = req.getParameter("username");
username = new String(username.getBytes("ISO8859-1"),"UTF-8");
```

__post 中文乱码__ 

由于客户端是以 UTF-8 字符编码将表单数据传输到服务端的，因此服务器也需要设置以 UTF-8 字符编码进行接收

解决方案：使用 ServletRequest 接口继承而来的 `setCharacterEncoding(charset)` 方法进行统一的编码设置

```java
req.setCharacterEncoding("UTF-8");
```

### 4.2 response 对象

response 对象用于响应请求并向客户端输出信息

__response 主要方法__ 

| 方法名称                       | 作用                               |
| ------------------------------ | ---------------------------------- |
| `setHeader(name,value)`        | 设置响应信息头                     |
| `setContentType(String)`       | 设置响应文件类型，响应式的编码格式 |
| `setCharacterEncoding(String)` | 设置服务端响应内容编码格式         |
| `getWriter()`                  | 获取字符输出流                     |

__解决输出中文乱码__ 

不推荐方式：

设置服务器端响应的编码格式

设置客户端响应内容的头内容的文件类型及编码格式

```java
response.setCharacterEncoding("UTF-8");
response.setHeader("Content-type","text/html;charset=UTF-8");
```

推荐方式：

同时设置服务端的编码格式和客户端的响应文件类型及响应时的编码格式

```java
response.setContentType("text/html;charset=UTF-8");
```

### 4.3 综合案例 Servlet + JDBC

数据库

```mysql
CREATE TABLE admin(
	uname VARCHAR(20) PRIMARY KEY,
    pwd VARCHAR(20) NOT NULL,
    tel VARCHAR(11) NOT NULL
);
```

数据

```mysql
INSERT INTO admin(uname, pwd, tel) VALUES ('USER1', 'abc', '12345612341');
INSERT INTO admin(uname, pwd, tel) VALUES ('USER2', 'bcd', '12345612342');
INSERT INTO admin(uname, pwd, tel) VALUES ('USER3', 'cde', '12345612343');
INSERT INTO admin(uname, pwd, tel) VALUES ('USER4', 'dfg', '12345612344');
INSERT INTO admin(uname, pwd, tel) VALUES ('USER5', 'fgh', '12345612345');
```

## 5. 转发与重定向

### 5.1 现有问题

业务逻辑与显示结果页面在一个 Servlet 里，不符合单一职能，各司其职的思想，不利于后续维护，应该将业务逻辑和显示结果分开

将 XXXServlet 分成 XXXController 和 XXXJSP

### 5.2 转发

将请求发送给服务器上的其他资源，共同完成一次请求的处理

- 页面跳转

  ```java
  request.getRequestDispatcher("/url-pattern"),forward(request,response);
  ```

  使用 forword 跳转时，是在服务器内部跳转，地址栏不会发生变化，属于同一次请求

- 数据传递

  forward 表示一个请求，是在服务内跳转，可以共享同一次 request 作用域中的数据

  - request 作用域：拥有存储数据的空间，作用范围是一次请求有效（一次请求可以经过多次转发）
    - 可以将数据存入 request 后，在一次请求过程中的任何位置进行获取
    - 可以传递任何数据
  - 存数据：`request.setAttribute(key, value);` 
    - 以键值对形式存储在 request 作用域中，key 为 String 类型，value 为 Object 类型
  - 取数据：`request.getAttribute(key);` 
    - 通过 String 类型的 key 访问 Object 类型的 Value

- 转发特点

  - 转发是服务器行为
  - 转发是浏览器只做了一次访问请求
  - 转发浏览器地址不变
  - 转发两次跳转之间传输信息不会丢失，所以可以通过 request 进行数据的传递
  - 转发只能将请求转发给同一个 web 应用中的组件

### 5.3 重定向

重定向作用域客户端，将请求发送给服务器后，服务器响应给客户端一个新的请求地址，客户端允许重新发送新请求

- 页面跳转

  在调用业务逻辑的 Servlet 中，使用以下代码

  ```java
  response.sendRedirect("uri-pattern");
  ```

  - URI：Uniform Resource Identifier 统一资源标识符，用来表示服务器中的一个定位

- 数据传递

  sendRedirect 跳转时，地址栏改变，代表客户端重新发送的请求，属于两次请求

  - response 没有作用域，两次 request 请求中的数据无法共享
  - 传递数据：通过 URI 的拼接进行数据传递 `/WebProject/b?username=a;` 
  - 获取数据：`String request,getParameter("username");` 

- 重定向特点
  - 重定向是客户行为
  - 重定向是浏览器做了至少两次的访问请求
  - 重定向浏览器地址改变
  - 重定向两次跳转之间传输的信息会丢失（request 范围）
  - 重定向可以指向任何的资源，包括当前应用程序中的其它资源，同一个站点上的其它应用程序中的资源，其它站点的资源

### 5.4 转发，重定向总结

当两个 Servlet 需要传递数据时，选择 forward 转发，不建议使用 sendRedirect 进行传递，明文传递是真的

## 6. Servlet 生命周期

### 6.1 生命周期四个阶段

- 实例化

  当用户第一次访问 Servlet 时，由容器调用 Servlet 的构造器创造具体的 Servlet 对象，也可以在容器启动之后立刻创建实例，使用如下代码可以设置 Servlet 是否在服务器启动时就创建

  ```xml
  <load-on-startup>1</load-on-startup>
  ```

  - 注意：只执行一次

- 初始化

  在初始化阶段，`init()` 方法被调用，这个方法在 `javax.servlet.Servlet` 接口中定义，其中，方法以一个 ServletConfig 类型的对象作为参数

  - init 方法只被执行一次

- 服务

  当客户端有一个请求时，容器就会将请求 ServletRequest 与响应 ServletResponse 对象转给 Servlet，以参数的形式传给 service 方法

  - 此方法能执行多次

- 销毁

  当 Servlet 容器停止或者重新启动都会引起销毁 Servlet 对象并调用 destroy 方法

  - destroy 方法执行一次

## 7. Servlet 线程安全问题

Servlet 在访问后，会执行实例化操作，创建一个 Servlet 对象，而 Tomcat 容器可以同时多个线程并发访问同一个 Servlet，如果在方法中对成员变量做修改操作，就会有线程安全问题

- 如何保证线程安全
  - synchronized
    - 将存在线程安全问题的代码放到同步代码块里
  - 实现 SingleThreadModel 接口
    - servlet 实现 SingleThreadModel 接口后，每个线程都会创建 servlet 实例，这样每个客户端请求就不存在共享资源的问题，但是 servlet 响应客户端请求的效率太低，已经淘汰
  - 尽可能使用局部变量

## 8. 状态管理

- 现有问题

  - HTTP 协议是无状态的，不能保存每次提交的信息

  - 如果用户发来一个新的请求，服务器无法知道它是否与上次的请求有联系
  - 对于那些需要多次提交请求才能完成的 Web 操作，比如登录来说，就成问题了

- 概念

  - 将浏览器与 web 之间多次交互当作一个整体来处理，并且将多次交互涉及的数据（状态）保存下来

- 状态管理分类

  - 客户端状态管理技术：将状态保存在客户端，Cookie 技术
  - 服务端状态管理技术：将状态保存在服务端，Session 技术（服务器传递 SessionID 时需要使用 Cookie 的方式）和 Application

## 9. Cookie 的使用

- 什么是 Cookie
  - Cookie 是在浏览器访问 Web 服务器的某个资源时，由 Web 服务器在 HTTP 响应消息头中附带传送给浏览器的一小段数据
  - 一旦 Web 浏览器保存了某个 Cookie，那么它以后每次访问该 Web 服务器时，都应在 HTTP 请求头中将这个 Cookie 回传给 Web 服务器
  - 一个 Cookie 主要由标识该信息的名称和值组成
- Cookie 原理
  - Client：访问登录 Servlet（首次请求）
  - Server：创建 Cookie 对象（key-value），通过 response 将 Cookie 响应给 Client
  - Client：接收 Servlet 响应的 Cookie，存储
  - Client：访问 Servlet
  - Server：从请求中查找 Cookie，存在就获取
  - Client：访问显示 Servlet，携带 Cookie

- 创建 Cookie

  - 通过 `response.addCookie(cookie);` 进行响应

  - `setMaxAge` 可以修改过期时间
  - `setPath` 设置 Cookie 许可获取路径

- 获取 Cookie

  - 通过 `request.getCookies` 获取到一个 Cookie 的数组（可能为空）

- 修改 Cookie

  - 修改 request 中获得的 Cookie，不能修改名字和路径
  - 如果有两个名字路径和路径对应相同的 Cookie 才会覆盖

- Cookie 编码与解码

  - Cookie 默认不支持中文，只能包含 ASCII 字符，所以 Cookie 需要对 Unicode 进行编码，否则会出现乱码
    - 编码可以使用 `java.net.URLEncoder` 类的 `encode(String str, String encoding)` 方法
    - 解码用 `java.net.URLDecoder` 类的 `decode(String str, String encoding` 方法

- Cookie 的优点和缺点
  - 优点
    - 可以自定义到期规则
    - 简单性：基于文本的轻量结构，包含简单的键值对
    - 数据持久性：默认在过期前是可以一直存在客户端浏览器上的
  - 缺点
    - 大小受到限制：4k，8k 字节限制
    - 用户配置为禁用
    - 潜在安全风险：可能被篡改

## 10. Session 对象

- Session 概述
  - Session 用于记录用户的状态，Session  指的是在一段时间内，单个客户端与 Web 服务器的一连串相关的交互过程
  - 在一个 Session 中，客户可能会多次请求访问同一个资源，也有可能请求访问各种不同的服务器资源
  
- Session 原理
  - 服务器会为每一次会话分配一个 Session 对象
  - 同一个浏览器发起的多次请求，属于同一次会话 Session
  - 首次使用到 Session，服务器会自动创建 Session，并创建 Cookie 存储 SessionId 发送回客户端
  
- Session 使用
  - Session 作用域：拥有存储数据的空间，作用范围是一次会话有效
    - 一次会话是使用同一浏览器发送的多次请求，一旦浏览器关闭，则会话结束
    - 可以将数据存入 Session 中，在一次会话的任意位置进行获取
    - 可传递任何数据
  
- Session 保存数据

  ```java
  session.setAttribute("key","value");
  ```

- Session 获取数据

  ```java
  session.getAttribute("key");
  ```

- Session 移除数据

  ```java
  session.removeAttribute("key");
  ```

- Session 与 Request 区别

  - Request 是一次请求有效，请求改变，则 request 改变
  - Session 是一次会话有效，浏览器改变，则 session 改变

- Session 的生命周期

  - 开始：第一次用到 Session 的请求产生，则创建 Session

  - 结束：

    - 浏览器关闭

    - Session 超时

      `session.setMaxInactiveInterval(seconds);` 最大有效时间

    - 手工销毁

      `session.invalidate();` 登出，注销

- 浏览器禁用 Cookie 解决方案

  - 浏览器禁用 Cookie 后果

    服务器在默认情况下，会使用 Cookie 的方式将 SessionID 发送给浏览器，如果用户禁止 Cookie，则 SessionID 不会被浏览器保存，此时，服务器可以使用如 URL 重写这样的方式来发送 SessionID

  - URL 重写

    浏览器在访问服务器上的某个地址时，不再使用原来的那个地址，而是使用经过修改的地址（即原来的地址后面加上 SessionID）

  - 实现 URL 重写

    `response.encodeRedirectURL(String url)` 生成重写的 URL

    ```java
    HttpSession session = request.getSession();
    session.setAttribute("uname","USER1");
    String url = response.encodeRedirectURL("/project/servletpage");
    // /project/servletpage;jsessionid=xxxxx
    response.sendRedirect(url);
    ```

## 11. ServletContext 对象

- ServletContext 概述

  - 全局对象，也拥有作用域，对应一个 Tomcat 中的 Web 应用
  - 当 Web 服务器启动时，会为每一个 Web 应用程序创建一块共享的存储区域（ServletContext）
  - ServletContext 在 Web 服务器启动的时候创建，服务器关闭时销毁

- 获取 ServletContext 对象（这三个对象是同一个）

  - GenericServlet 提供了 `getServletContext()` 方法（`this.getServletContext();`）
  - HttpServletRequest 提供了 `getServletContext()` 方法
  - HttpSession 提供了 `getServletContext()` 方法

- ServletContext 作用

  - 获取项目真实路径（磁盘路径）

    ```java
    String realPath = servletContext.getRealPath("/");
    // '/' 当前项目
    ```

  - 获取项目上下文路径

    ```java
    servletContext.getContextPath();
    request.getContextPath();
    ```

  - 全局容器

    ServletContext 拥有作用域，可以存储数据到全局容器中

    - 存储数据：`servletContext.setAttribute("name",value);` 
    - 获取数据：`servletContext.getAttribute("name");` 
    - 移除数据：`servletContext.removeAttribute("name");` 

- ServletContext 特点

  - 唯一性：一个应用对应一个 servlet 上下文
  - 生命周期：只要容器不关闭或者应用不卸载，servlet 上下文就一直存在

- ServletContext 应用场景

  ServletContext 统计当前项目访问次数

- 作用域总结

  - HttpServletRequest：一次请求，请求响应之前有效
  - HttpSession：一次会话开始，浏览器不关闭或不超时之前有效
  - ServletContext：服务器启动开始，服务器停止之前有效

## 12. 过滤器 Filter

- 现有问题

  在以往的 Servlet 中，有没有冗余的代码，多个 Servlet 都要进行编写

- 概念

  过滤器是处于客户端与服务器目标资源之间的一道过滤技术

  Client -> 过滤器 -> 目标资源 -> 过滤器 -> Client

- 过滤器作用

  - 执行地位在 Servlet 之前，客户端发送请求时，会经过 Filter， 再达到目标 Servlet 中，响应时，会根据执行流程再次反向执行 Filter
  - 可以解决多个 Servlet 共性代码的冗余问题（乱码处理，登陆验证）

- 编写过滤器

  Servlet API 中提供了一个 Filter 接口，只要实现了这个接口即可

- 过滤器配置

  - 注解配置

    ```java
    @WebFilter(value = "/target")
    ```

  - xml 配置

    在 web.xml 中进行过滤器配置

    ```xml
    <filter>
        <filter-name>target</filter-name>
        <filter-class>com.ayy.filter.MyFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>target</filter-name>
        <url-pattern>/target</url-pattern>
    </filter-mapping>
    ```

  - 关于拦截路径

    - 精确拦截匹配

      `/index.jsp`，`/myservlet` 

    - 后缀拦截匹配

      `*.jsp`，`*.html`，`*.jpg` 

    - 通配符拦截匹配

      `/*` 

- 过滤器链和优先级

  - 过滤器链

    客户端对服务器请求之后，服务器调用 Servlet 之前会执行一组过滤器（多个多滤器），那么这组过滤器就称为一条过滤器

    每个过滤器实现某个特定的功能，当第一个 Filter 的 doFilter 方法被调用时，Web 服务器会创建一个代表 Filter 链的 FilterChain 对象传递给该方法，在 doFilter 方法中，开发人员如果调用了 FilterChain 对象的 doFilter 方法，则 Web 服务器会检查 FilterChain 对象中是否还有 Filter，如果有，则调用第二个 Filter，如果没有，则调用目标资源

  - 过滤器优先级

    在一个 Web 应用中，可以写多个 Filter，这些 Filter 组合起来称之为一个 Filter 链

    优先级：

    - 如果是注解，则按照类全限定名的字符串决定作用顺序
    - 如果是 web.xml，按照 filter-mapping 注册顺序，从上往下
    - web.xml 配置高于注解方式
    - 如果注解和 web.xml 同时配置，会创建多个过滤器对象，造成多次过滤

- 过滤器典型应用

  - 过滤器解决编码

    ```java
    servletRequest.setCharacterEncoding("GBK");
    servletResponse.setContentType("text/html;charset=utf-8");
    filterChain.doFilter(servletRequest, servletResponse);
    ```

  - 权限应用

    ```java
    HttpServletRequest req = (HttpServletRequest) servletRequest;
    HttpServletResponse resp = (HttpServletResponse) servletResponse;
    HttpSession session = req.getSession();
    Object obj = session.getAttribute("obj");
    if(obj!=null){
        filterChain.doFilter(req, resp);
    }else {
        resp.sendRedirect(req.getContextPath()+"/login.html");
    }
    ```

## 13. 综合案例（EMS）

- 创建数据表

  ```mysql
  CREATE TABLE emp(
    eid INT PRIMARY KEY AUTO_INCREMENT,
    ename VARCHAR(20) NOT NULL,
    salary DOUBLE NOT NULL,
    age INT NOT NULL
  );
  
  CREATE TABLE empmanager(
    uname VARCHAR(20) NOT NULL,
    pwd VARCHAR(20) NOT NULL
  );
  ```

  准备数据

  ```mysql
  INSERT INTO emp(ename,salary,age) VALUES('emp1',3000,20);
  INSERT INTO emp(ename,salary,age) VALUES('emp2',4000,22);
  INSERT INTO emp(ename,salary,age) VALUES('emp3',5000,24);
  
  INSERT INTO empmanager(uname,pwd) VALUES('mgr','123456')
  ```

- 工具类

  ```java
  public class DBUtils {
      private static DruidDataSource dataSource;
      private static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<>();
  
      static {
          Properties prop = new Properties();
          InputStream is = DBUtils.class.getClassLoader().getResourceAsStream("aliyun.properties");
          try {
              prop.load(is);
              dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(prop);
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
  
      public static Connection getConnection() {
          Connection connection = null;
          connection = THREAD_LOCAL.get();
          try {
              if (connection == null) {
                  connection = dataSource.getConnection();
                  THREAD_LOCAL.set(connection);
              }
          } catch (SQLException throwables) {
              throwables.printStackTrace();
          }
          return connection;
      }
  
      public static void beginTX(){
          Connection connection = null;
          try {
              connection = DBUtils.getConnection();
              connection.setAutoCommit(false);
          } catch (SQLException throwables) {
              throwables.printStackTrace();
          }
      }
  
      public static void commit(){
          Connection connection = null;
          try {
              connection = getConnection();
              connection.commit();
          } catch (SQLException throwables) {
              throwables.printStackTrace();
          }finally {
              DBUtils.close(connection);
          }
      }
  
      public static void rollback(){
          Connection connection = null;
          try {
              connection = getConnection();
              connection.commit();
          } catch (SQLException throwables) {
              throwables.printStackTrace();
          }finally {
              DBUtils.close(connection);
          }
      }
  
      public static void close(Connection connection, AutoCloseable... clos){
          if(connection!=null) {
              try {
                  connection.close();
                  THREAD_LOCAL.remove();
              } catch (SQLException throwables) {
                  throwables.printStackTrace();
              }
          }
          for (AutoCloseable clo : clos) {
              if(clo!=null){
                  try {
                      clo.close();
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              }
          }
      }
  }
  ```


