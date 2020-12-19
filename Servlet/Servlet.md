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

将 CATALINA_HOME/lib/servlet-api.jar 目录放到环境变量的 classpath 中

编写 servlet

​	实现 javax.servlet.Servlet

​	重写5个方法

​	在核心的 service() 方法中编写输出语句，打印访问结果

```java
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
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
    <servlet>
        <servlet-name>my</servlet-name><!--类代替名-->
        <servlet-class>MyServlet</servlet-class><!--类名-->
    </servlet>
    <servlet-mapping>
        <servlet-name>my</servlet-name><!--映射的类代替名-->
        <url-pattern>/myservlet</url-pattern><!--对应的访问位置 <项目名/访问位置>-->
    </servlet-mapping>
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

​	doGet，如果 servlet 支持 HTTP GET 请求

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

​	精确匹配	/具体名称	只有 url 路径是具体的名称的时候才会触发 servlet

​	后缀匹配	*.xxx	只要是以 .xxx 结尾的就匹配触发 servlet

​	通配符匹配	/*	匹配所有请求，包含服务器所有资源

​	通配符匹配	/	匹配所有请求，包含服务器的所有资源，不包括 jsp

load-on-startup

1. 元素标记容器是否应该在 web 应用程序启动的时候加载这个 servlet
2. 它的值必须是一个整数，表示 servlet 被加载的先后顺序
3. 如果该元素的值为负数或者没有设置，则容器会当 servlet 被请求时再加载
4. 如果值为正整数或者0时，表示容器在应用启动的时候就加载初始化这个 servlet，值越小，servlet 的优先级越高，就越优先被加载。值相同时，容器会自己选择顺序来加载

__注解 (servlet 3.0+)__

@WebServlet

​	name：servlet 名字 （可选）

​	value：配置 url 路径，可以配置多个

​	urlPatterns：配置 url 路径，不能和 value 同时使用

​	loadOnStartup：配置 servlet 创建时机，如果是0或者正数，启动程序时创建，如果是负数，则访问时创建，数字越小优先级越高

与 web.xml 不冲突

## 4. Servlet 应用

### 4.1 request 对象

get 和 post

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

request 主要方法

​	两个核心接口 ServletRequest 和 HttpServletRequest