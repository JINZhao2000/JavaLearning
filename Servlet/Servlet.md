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

将 CATALINA_HOME/lib/com.ayy.servlet-api.jar 目录放到环境变量的 classpath 中

编写 com.ayy.servlet

​	实现 javax.com.ayy.servlet.Servlet

​	重写5个方法

​	在核心的 service() 方法中编写输出语句，打印访问结果

```java
import javax.com.ayy.servlet.Servlet;
import javax.com.ayy.servlet.ServletConfig;
import javax.com.ayy.servlet.ServletRequest;
import javax.com.ayy.servlet.ServletResponse;
import javax.com.ayy.servlet.ServletException;
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
    <com.ayy.servlet>
        <com.ayy.servlet-name>my</com.ayy.servlet-name><!--类代替名-->
        <com.ayy.servlet-class>MyServlet</com.ayy.servlet-class><!--类名-->
    </com.ayy.servlet>
    <com.ayy.servlet-mapping>
        <com.ayy.servlet-name>my</com.ayy.servlet-name><!--映射的类代替名-->
        <url-pattern>/myservlet</url-pattern><!--对应的访问位置 <项目名/访问位置>-->
    </com.ayy.servlet-mapping>
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

​	doGet，如果 com.ayy.servlet 支持 HTTP GET 请求

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

​	精确匹配	/具体名称	只有 url 路径是具体的名称的时候才会触发 com.ayy.servlet

​	后缀匹配	*.xxx	只要是以 .xxx 结尾的就匹配触发 com.ayy.servlet

​	通配符匹配	/*	匹配所有请求，包含服务器所有资源

​	通配符匹配	/	匹配所有请求，包含服务器的所有资源，不包括 jsp

load-on-startup

1. 元素标记容器是否应该在 web 应用程序启动的时候加载这个 com.ayy.servlet
2. 它的值必须是一个整数，表示 com.ayy.servlet 被加载的先后顺序
3. 如果该元素的值为负数或者没有设置，则容器会当 com.ayy.servlet 被请求时再加载
4. 如果值为正整数或者0时，表示容器在应用启动的时候就加载初始化这个 com.ayy.servlet，值越小，com.ayy.servlet 的优先级越高，就越优先被加载。值相同时，容器会自己选择顺序来加载

__注解 (com.ayy.servlet 3.0+)__

@WebServlet

​	name：com.ayy.servlet 名字 （可选）

​	value：配置 url 路径，可以配置多个

​	urlPatterns：配置 url 路径，不能和 value 同时使用

​	loadOnStartup：配置 com.ayy.servlet 创建时机，如果是0或者正数，启动程序时创建，如果是负数，则访问时创建，数字越小优先级越高

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

