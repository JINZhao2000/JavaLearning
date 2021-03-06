# Struts2

## 1. 基础

开发模式

- Jsp 内嵌 JavaBean DB
  - 执行效率高
  - 开发效率高
  - 适合小型项目
  - 逻辑紊乱
  - 页面混乱
  - 维护困难

- MVC Jsp Servlet JavaBean DB 视图结构分离
  - 结构清晰
  - 低耦合，维护方便，易扩展
  - 执行效率相对底，代码量大，重复代码多
  - Servlet -> 
    - 将一个 url 映射到一个 Java 类的处理方法上
    - 接受请求数据
    - 如何将处理结果展示到页面
    - 如何进行页面的跳转

## 2. Struts HelloWorld

- jar 包

  ```xml
  <dependency>
  	<groupId>org.apache.struts</groupId>
  	<artifactId>struts2-core</artifactId>
  	<version>2.5.25</version>
  </dependency>
  <dependency>
  	<groupId>asm</groupId>
  	<artifactId>asm-commons</artifactId>
  	<version>3.3</version>
  </dependency>
  ```

- 配置 Struts2 前端控制器

  web.xml

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
          <url-pattern>/*.action</url-pattern>
      </filter-mapping>
      <welcome-file-list>
          <welcome-file>index.jsp</welcome-file>
      </welcome-file-list>
  </web-app>
  ```

- 添加 struts2 的配置文件

  struts.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE struts PUBLIC
          "-//Apache Software Foundation//DTD Struts Configuration 2.5//EN"
          "http://struts.apache.org/dtds/struts-2.5.dtd">
  <struts>
  	<package name="ayy" namespace="/" extends="struts-default">
          <action name="hello" class="com.ayy.action.HelloAction">
              <result>/hello.jsp</result>
          </action>
      </package>
  </struts>
  ```

- 新建 struts 处理类

  ```java
  public class HelloAction {
      /**
       * In struts2, all method of transaction is public without parameters
       *   return String
       *   the default method is named execute
       * @return
       */
      public String excute(){
          System.out.println("Hello World");
          return "success";
      }
  }
  ```

- hello.jsp 页面

  ```jsp
  <%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
  <html>
      <head>
          <title>Struts2 Sample Hello World</title>
      </head>
      <body>
          <h1>Hello Struts2</h1>
      </body>
  </html>
  ```

## 3. Struts 配置

- web.xml

  过滤器：filter

  Struts2 框架开始工作的入口，接管请求：

  org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter

- struts.xml

  该配置文件名称固定，并且只能放于 resources 下

  \<package\>：分模块管理

  - name 自定义，但是不能重复
  - namespace 命名空间 和 url 请求路径直接相关
  - extends 必须直接或者间接继承 struts-default

  \<action\>：action 请求

  - name 请求名称

  - class 处理类的全限定名，如果不配置，则由默认类来处理

    ActionSupport.java

  - method 执行 action 的方法

  \<result\>：结果集设置

  - name 结果集名称，和处理方法的返回值匹配，默认为 success
    - Action.SUCCESS 执行成功，跳转到下一个试图
    - Action.NONE 执行成功，不需要视图显示
    - Action.ERROR 执行失败，显示失败页面
    - Action.INPUT 要执行该 action 需要更多的输入条件
  - type 指定响应结果类型
    - dispatcher 转发 默认
    - redirect 重定向
    - freemarker
    - velocity
  - 值为跳转页面 不加 / 为相对 namespace 路径

## 4. Struts 执行流程

1. 发起请求
2. 服务器接收请求
3. 服务器将交给 struts2 的前端控制器
4. 根据请求的 url 查看 struts.xml 中的 namespace + actionName
5. 交给 action 代理类
6. 执行 action 所有对应类的对应方法
7. 根据方法的执行结果到 action 的结果集进行匹配
8. 响应结果
9. 将结果交给服务器处理
10. response()

------

Browser --1-> Server --2,3-> StrutsPrepareAndExecuteFilter --4-> ActionMapping --5-> ActionProxy 6 7 --8-> StrutsPrepareAndExecuteFilter --9-> Server --10-> Browser

------

![Struts2-Architecture](./images/Struts2-Architecture.png)

## 5. 数据处理

使用 struts2 获取表单数据

在 struts2 中有三种处理表单的方式：__属性驱动，对象驱动，模型驱动__ 

### 5.1 属性驱动

只需表单域名称和 action 处理类地属性名称一致，并且提供 __set__ 方法，那么在 action 处理类中即可获得表单数据

这种获取数据地方式称为__属性驱动__  （通过 IoC）

处理类

LoginAction.java

避免 Field 里面出现单个字母在开头，get 方法不会把这个字母变为大写，但是 struts 会把 Field 第一个字母变为大写

```java
public class LoginAction {
    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login(){
        System.out.println("username : "+username+"\tpassword : "+password);
        return Action.SUCCESS;
    }
}
```

页面

login.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Data Process</title>
</head>
<body>
<form action="login.action" method="post">
    username:<input type="text" name="username"/><br>
    password:<input type="password" name="password"/><br>
    <input type="submit" value="login"/>
</form>
</body>
</html>
```

struts.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE struts PUBLIC
        "-//Apache Software Foundation//DTD Struts Configuration 2.5//EN"
        "http://struts.apache.org/dtds/struts-2.5.dtd">
<struts>
    <package name="ayy" namespace="/" extends="struts-default">
        <action name="login" class="com.ayy.action.LoginAction" method="login">
            <result name="success">/success.jsp</result>
        </action>
    </package>
</struts>
```

success.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Success</title>
</head>
<body>
Success : ${username}
</body>
</html>
```

如果数据需要显示到页面上，那么该数据可以作为处理类的属性，处理方法后该属性有值，并且有该属性的 __get__ 方法，那么页面上可以直接通过 el 表达式获取

### 5.2 对象驱动

在 Action 处理类中，属性以对象方式存在，该属性对象，只需声明即可，该对象必须要有无参构造方法，并提供 __get/set__ 方法，在表单域中的表单域名称以 __属性对象名.属性对象的属性__ 来命名

UserVO.java

```java
public class UserVO {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```

处理类 LoginAction.java

```java
public class LoginAction {
    // a constructor without parameter is required
    private UserVO user;

    public String login(){
        System.out.println("username : "+user.getUsername()+"\tpassword : "+user.getPassword());
        return Action.SUCCESS;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }
}
```

struts 配置 struts.xml

```xml
<struts>
    <package name="ayy" namespace="/" extends="struts-default">
        <action name="login" class="com.ayy.action.LoginAction" method="login">
            <result name="success">/success.jsp</result>
        </action>
    </package>
</struts>
```

页面显示 login.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Data Process</title>
</head>
<body>
<form action="login.action" method="post">
    username:<input type="text" name="user.username"/><br>
    password:<input type="password" name="user.password"/><br>
    <input type="submit" value="login"/>
</form>
</body>
</html>
```

### 5.3 模型驱动

解决对象驱动的一个缺点，在对象驱动中，页面的表单域名称比较复杂，如果对象属性比较多的情况下，代码量比较大，通过模型驱动可以解决这个问题，需要主动创建对象

Action 类需要实现 ModelDriven\<T\> 接口

LoginAction.java

```java
public class LoginAction implements ModelDriven<UserVO> {
    // a constructor without parameter is required
    private UserVO user = new UserVO();

    public String login(){
        System.out.println("username : "+user.getUsername()+"\tpassword : "+user.getPassword());
        return Action.SUCCESS;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    @Override
    public UserVO getModel() {
        return user;
    }
}
```

页面 login.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Data Process</title>
</head>
<body>
<form action="login.action" method="post">
    username:<input type="text" name="username"/><br>
    password:<input type="password" name="password"/><br>
    <input type="submit" value="login"/>
</form>
</body>
</html>
```

## 6. Action 的几种创建方式

### 6.1 通过实现 Action 接口创建 Action 类

Action 类

```java
public class HelloAction implements Action {
    @Override
    public String execute() throws Exception {
        System.out.println("Action Interface");
        return Action.SUCCESS;
    }
}
```

struts 配置

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE struts PUBLIC
        "-//Apache Software Foundation//DTD Struts Configuration 2.5//EN"
        "http://struts.apache.org/dtds/struts-2.5.dtd">
<struts>
    <package name="ayy" namespace="/" extends="struts-default">
        <action name="hello" class="com.ayy.action.HelloAction">
            <result>/index.jsp</result>
        </action>
    </package>
</struts>
```

web.xml

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
</web-app>
```

index.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Action Interface</title>
</head>
<body>
    Action executed
</body>
</html>
```

### 6.2 通过继承 ActionSupport 类来创建 Action 处理类，struts2 推荐

HelloAction.java

```java
/**
 * @ Provided by ActionSupport : Data Validation, Globalization, Localization
 */

public class HelloAction extends ActionSupport {
    @Override
    public String execute() throws Exception {
        System.out.println("ActionSupport Class");
        return super.execute();
    }
}
```

### 6.3 无侵入式

前几章的都是无侵入式

## 7. Struts 配置2

### 7.1 常量配置

修改 struts 响应请求后缀

```xml
<!-- extension configuration -->
<constant name="struts.action.extension" value="mypost,action,,"/>
```

修改拦截请求名

```xml
<filter>
	<filter-name>struts2</filter-name>
	<filter-class>org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter</filter-class>
</filter>
<filter-mapping>
	<filter-name>struts2</filter-name>
	<url-pattern>*.mypost</url-pattern>
    <!-- /* -->
</filter-mapping>
```

struts 配置来自 default.properties

default.properties

__乱码解决__

```properties
struts.i18n.encoding=UTF-8
```

i18n 是数字 18 不是字母 l

internationalization i 与 n 中间有 18 个字母

__每次请求自动加载配置__

```properties
struts.devMode = true
### when set to true, Struts will act much more friendly for developers. This
### includes:
### - struts.i18n.reload = true
### - struts.configuration.xml.reload = true
### - raising various debug or ignorable problems to errors
###   For example: normally a request to foo.action?someUnknownField=true should
###                be ignored (given that any value can come from the web and it
###                should not be trusted). However, during development, it may be
###                useful to know when these errors are happening and be told of
###                them right away.
```

__端口__

```properties
struts.url.http.port = 80
struts.url.https.port = 443
```

### 7.2 Include

```xml
<include file="directory/directory/file"/>
```

## 8 Action 的优化配置

Struts2 中提供 3 中方式解决 Action 配置文件膨胀的问题

传统类：

```java
public class UserAction {
    public String save(){
        System.out.println("save");
        return Action.SUCCESS;
    }
    public String delete(){
        System.out.println("delete");
        return Action.SUCCESS;
    }
    public String update(){
        System.out.println("update");
        return Action.SUCCESS;
    }
    public String query(){
        System.out.println("query");
        return Action.SUCCESS;
    }
}
```

传统配置文件

```xml-dtd
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE struts PUBLIC
        "-//Apache Software Foundation//DTD Struts Configuration 2.5//EN"
        "http://struts.apache.org/dtds/struts-2.5.dtd">
<struts>
    <package name="ayy" namespace="/" extends="struts-default">
        <action name="save" class="com.ayy.action.UserAction" method="save">
            <result type="redirectAction">query</result>
        </action>
        <action name="delete" class="com.ayy.action.UserAction" method="delete">
            <result type="redirectAction">query</result>
        </action>
        <action name="update" class="com.ayy.action.UserAction" method="update">
            <result type="redirectAction">query</result>
        </action>
        <action name="query" class="com.ayy.action.UserAction" method="query">
            <result type="redirectAction">/list.jsp</result>
        </action>
    </package>
</struts>
```

### 8.1 动态方法调用

- 在常量中开启动态方法调用

  ```xml
  <constant name="struts.enable.DynamicMethodInvocation" value="true"/>
  ```

- 修改特定返回值

  ```java
  public String query(){
  	System.out.println("query");
  	return "list";
  }
  ```

- 配置 action

  ```xml
  <package name="ayy" namespace="/" extends="struts-default">
  	<global-allowed-methods>save,select,update,query</global-allowed-methods>
  	<action name="userAction" class="com.ayy.action.UserAction">
  		<result type="redirect">userAction!query.action</result>
  		<result name="list">/list.jsp</result>
  	</action>
  </package>
  ```

- 调用处理方法

  ```html
  http://..../actionName!methodName.action
  ```

不推荐使用：不安全

### 8.2 通配符配置

- action 通配符 * {1} 数字代表第几个 *

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE struts PUBLIC
          "-//Apache Software Foundation//DTD Struts Configuration 2.5//EN"
          "http://struts.apache.org/dtds/struts-2.5.dtd">
  <struts>
      <constant name="struts.enable.DynamicMethodInvocation" value="true"/>
      <package name="ayy" namespace="/" extends="struts-default">
          <global-allowed-methods>save,select,update,query</global-allowed-methods>
          <action name="*" class="com.ayy.action.UserAction" method="{1}">
              <result type="redirect">query.action</result>
              <result name="list">/list.jsp</result>
          </action>
      </package>
  </struts>
  ```

- 先匹配没有通配符的

### 8.3 注解

- 需求 jar 包

  ```xml
  <dependency>
  	<groupId>org.apache.struts</groupId>
  	<artifactId>struts2-convention-plugin</artifactId>
  	<version>2.5.25</version>
  </dependency>
  ```

- 在处理类上写注解

  ```java
  @ParentPackage("struts-default")
  @Namespace("/")
  @Result(name="list",location="/list.jsp")
  public class UserAction {
  
      public String save(){
          System.out.println("save");
          return "success";
      }
  
      public String delete(){
          System.out.println("delete");
          return "success";
      }
  
      public String update(){
          System.out.println("update");
          return "success";
      }
  
      @Action("query")
      public String query(){
          System.out.println("query");
          return "list";
      }
  }
  ```

## 9. ThreadLocal 和 ActionContext

### 9.1 ActionContext 

是一个 Map 全局对象，贯穿整个 Action 的执行生命周期，每次接受请求后都会创建一个新的 ActionContext 对象，实现了 Struts2 与 Servlet 解耦，测试不依赖于容器，线程安全，执行效率比 Servlet 低

- Request - Map：HttpServletRequest 域中的数据
- Session：HttpSession 域中的数据
- Application：存放 ServletContext 域中的数据
- Parameters：存放的请求参数（url）
- Attr：Request, Session, Application 中的数据
- __ValueStack__：业务处理类的相关属性

## 10. OGNL

Object Graph Navigation Language

- 数据设置
- 类型转换

依赖 jar 包：ognl + javassist

思想：把数据分为两类：常用的和不常用的

```java
Ognl.getValue("",context,root); // 取值，不常用（map）->"#name"，常用（object）->"name"
```

Struts2 中是通过 ognl 来设值和取值的，ActionContext 作为 ognl 的上下文对象，ValueStack 作为 ognl 的 root 对象

在 struts2 中使用 ognl 表达式获取数据需要使用 struts2 的标签库，需要注意页面一定是过滤器后才能解析 struts 对象

jsp 配置

```jsp
<%@taglib prefix="s" uri="/struts-tags"%>
...
<body>
    <s:property value="name"/>
    <s:property value="#application.name"/>
</body>
```

web.xml 配置

```xml
<web-app>
	<jsp-config>
        <taglib>
            <taglib-uri>/struts-tags</taglib-uri>
            <taglib-location>WEB-INF/struts-tags.tld</taglib-location>
        </taglib>
    </jsp-config>
</web-app>
```

然后将 struts2-core 的 META-INF 下 的 struts-tags.tld 拷贝到 WEB-INF 下

## 11. Sevlet API

需要把用户数据保存到 session 中

- 解耦方式获取

  java 文件

  ```java
  public class LoginAction {
      private User user;
  
      public String login(){
          if("abc".equals(user.getUsername())&&"1111".equals(user.getPassword())){
              ActionContext.getContext().getSession().put("currentUser",user);
              ActionContext.getContext().getApplication();
              Map<String,Object> req = (Map<String, Object>) ActionContext.getContext().get("request");
              req.put("pwd",user.getPassword());
              return Action.SUCCESS;
          }
          System.out.println("username : "+user.getUsername()+"\tpassword : "+user.getPassword());
          return Action.LOGIN;
      }
  
      public User getUser() {
          return user;
      }
  
      public void setUser(User user) {
          this.user = user;
      }
  }
  ```

  struts.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE struts PUBLIC
          "-//Apache Software Foundation//DTD Struts Configuration 2.5//EN"
          "http://struts.apache.org/dtds/struts-2.5.dtd">
  <struts>
      <package name="ayy" namespace="/" extends="struts-default">
          <action name="login" class="com.ayy.action.LoginAction" method="login">
              <result name="success">/success.jsp</result>
              <result name="login">/login.jsp</result>
          </action>
      </package>
  </struts>
  ```

  web.xml

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
      <welcome-file-list>
          <welcome-file>login.jsp</welcome-file>
      </welcome-file-list>
      <jsp-config>
          <taglib>
              <taglib-uri>/struts-tags</taglib-uri>
              <taglib-location>WEB-INF/struts-tags.tld</taglib-location>
          </taglib>
      </jsp-config>
  </web-app>
  ```

  jsp 配置

  ```jsp
  <%@ taglib prefix="s" uri="/struts-tags" %>
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <html>
  <head>
      <title>Success</title>
  </head>
  <body>
  Success
  Name : ${sessionScope.currentUser.username}
  Password : ${pwd}
  <%--${requestScope.pwd}--%>
  </body>
  </html>
  ```

- 耦合方式 - 3 种写法都是 1 种实现方式

  - 直接通过 ServletActionContext 来获取（推荐）

    java 配置

    ```java
    HttpServletRequest req = ServletActionContext.getRequest();
    req.getSession().setAttribute("currentUser",user);
    ```

  - 通过 ActionContext 对象获取 HttpServletRequest 对象

    java 配置

    ```java
    HttpServletRequest req = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
    req.getSession().setAttribute("currentUser",user);
    ```

  - 通过 IoC 的方式直接获取到 ServletAPI 对象

    java 配置

    ```java
    public class LoginAction4 implements ServletRequestAware {
        // a constructor without parameter is required
        private User user;
        private HttpServletRequest req;
        @Override
        public void setServletRequest(HttpServletRequest request) {
            this.req = request;
        }
    
        public String login(){
            if("abc".equals(user.getUsername())&&"1111".equals(user.getPassword())){
                req.getSession().setAttribute("currentUser",user);
                return Action.SUCCESS;
            }
            System.out.println("username : "+user.getUsername()+"\tpassword : "+user.getPassword());
            return Action.LOGIN;
        }
    
        public User getUser() {
            return user;
        }
    
        public void setUser(User user) {
            this.user = user;
        }
    }
    ```

## 12. Struts 类型转换

对常用数据类型自动进行了类型转换

自定义类型转换器

- 定义一个 POJO

  ```java
  public class Point {
      private int x;
      private int y;
  
      public int getX() {
          return x;
      }
  
      public void setX(int x) {
          this.x = x;
      }
  
      public int getY() {
          return y;
      }
  
      public void setY(int y) {
          this.y = y;
      }
  
      @Override
      public String toString() {
          return "Point(x="+x+", y="+y+"}";
      }
  }
  ```

- 新建一个类型转换器类，继承 StrutsTypeConverter 类（或者 DefaultConverter）

  ```java
  public class PointConverter extends StrutsTypeConverter {
      @Override
      public Object convertFromString(Map context, String[] values, Class toClass) {
          String value = values[0];
          Point point = new Point();
          int index = value.indexOf(",");
          point.setX(Integer.parseInt(value.substring(1,index)));
          point.setY(Integer.parseInt(value.substring(index+1,value.length()-1)));
          return point;
      }
  
      // 使用 ognl 表达式会调用该方法
      @Override
      public String convertToString(Map context, Object o) {
          return o.toString();
      }
  }
  ```

  配置 Action

  Java 类

  ```java
  public class PointAction {
      private Point point;
  
      public String execute(){
          System.out.println("x="+point.getX()+"\ty="+point.getY());
          return "success";
      }
  
      public Point getPoint() {
          return point;
      }
  
      public void setPoint(Point point) {
          this.point = point;
      }
  }
  ```

  struts.xml

  ```java
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE struts PUBLIC
          "-//Apache Software Foundation//DTD Struts Configuration 2.5//EN"
          "http://struts.apache.org/dtds/struts-2.5.dtd">
  <struts>
      <package name="ayy" namespace="/" extends="struts-default">
          <action name="add" class="com.ayy.action.PointAction">
              <result name="success">/success.jsp</result>
          </action>
      </package>
  </struts>
  ```

- 在 resources 中添加 xwork-conversion.properties

  ```properties
  # be converted type = converter
  com.ayy.vo.Point=com.ayy.converter.PointConverter
  ```

- jsp

  获取

  ```jsp
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <html>
  <head>
      <title>Data Process</title>
  </head>
  <body>
  <form action="add.action" method="post">
      Point:<input type="text" name="point"/><br>
      <input type="submit" value="submit"/>
  </form>
  </body>
  </html>
  ```

  显示

  ```jsp
  <%@ taglib prefix="s" uri="/struts-tags" %>
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <html>
  <head>
      <title>Success</title>
  </head>
  <body>
  (${point.x},${point.y})<br/>
  ${point}<br/>
  <s:property value="point"/>
  </body>
  </html>
  ```

## 13. 数据校验

前端数据校验：js（可以禁用 js 来规避数据校验）-> 后端数据校验

如果使用 Struts2 的数据校验功能，action 需要继承 ActionSupport 类，该类的方法中提供了一个 Validate 方法，可以将验证规则写在方法中，只有方法执行通过后，才会执行业务方法

步骤

- 业务处理类继承 ActionSupport

  ```java
  public class UserAction extends ActionSupport {
      private User user;
  
      public String execute(){
          System.out.println(user);
          return Action.SUCCESS;
      }
  
      @Override
      public void validate() {
          if (user.getUsername().length()<4 || user.getUsername().length()>10) {
              this.addFieldError("user.username","Error of username");
          }
          if (user.getAge() <= 0) {
              this.addFieldError("user.age","Error of age");
          }
      }
  
      public User getUser() {
          return user;
      }
  
      public void setUser(User user) {
          this.user = user;
      }
  }
  ```

- 重写 validate 方法

- 配置 input 结果集

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE struts PUBLIC
          "-//Apache Software Foundation//DTD Struts Configuration 2.5//EN"
          "http://struts.apache.org/dtds/struts-2.5.dtd">
  <struts>
      <package name="ayy" namespace="/" extends="struts-default">
          <action name="save" class="com.ayy.action.UserAction">
              <result name="input">/save.jsp</result>
              <result>/success.jsp</result>
          </action>
          <action name="save" class="com.ayy.action.UserFormAction" method="save">
              <result name="input">/save.jsp</result>
              <result>/success.jsp</result>
          </action>
      </package>
  </struts>
  ```

- web.xml 配置 struts-tags.tld 和 filter

- 添加标签 `<s:fielderror fieldName=""/> ` 

问题：一个业务类中有多个业务方法，每个业务校验规则可能不一样，但是所有业务方法都会通过 validate，导致功能不能实现

需要每个业务方法加上自己的验证方法，验证方法的命名规则为 validate+业务方法名（大写）

执行顺序为 validateXxxx -> validate -> xxxx

validate 方法中填写的是公共的校验规则

----

使用 Struts2 提供的校验框架进行校验

步骤

- 创建 jsp 页面，配置 struts-tag

  ```jsp
  <%@ taglib prefix="s" uri="/struts-tags" %>
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <html>
  <head>
      <title>Data Process</title>
  </head>
  <body>
  <form action="save.action" method="post">
      username:<input type="text" name="user.username"/><s:fielderror fieldName="user.username"/><br>
      sex:<input type="text" name="user.sex"/><br>
      age:<input type="number" name="user.age"/><br>
      <input type="submit" value="save"/>
  </form>
  </body>
  </html>
  ```

  web.xml

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
      <filter-mapping>
          <filter-name>struts2</filter-name>
          <url-pattern>*.jsp</url-pattern>
      </filter-mapping>
      <jsp-config>
          <taglib>
              <taglib-uri>/struts-tags</taglib-uri>
              <taglib-location>/WEB-INF/struts-tags.tld</taglib-location>
          </taglib>
      </jsp-config>
      <welcome-file-list>
          <welcome-file>save.jsp</welcome-file>
      </welcome-file-list>
  </web-app>
  ```

- 创建 action 类

  ```java
  public class UserFormAction extends ActionSupport {
      private User user;
  
      public String save(){
          System.out.println(user);
          return Action.SUCCESS;
      }
  
      public User getUser() {
          return user;
      }
  
      public void setUser(User user) {
          this.user = user;
      }
  }
  ```

- 在包下添加校验规则文件，命名为 XxxAction-validation.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE validators PUBLIC
          "-//Apache Struts//XWork Validator 1.0//EN"
          "http://struts.apache.org/dtds/xwork-validator-1.0.dtd">
  <validators>
      <field name="user.username">
          <field-validator type="requiredstring">
              <message>Named required</message>
          </field-validator>
          <field-validator type="stringlength">
              <param name="maxLength">10</param>
              <param name="minLength">4</param>
              <message>Name length should between ${minLength} and ${maxLength}</message>
          </field-validator>
      </field>
  </validators>
  ```

- 并且 pom.xml 里再加入下面的代码

  ```java
  <build>
      <resources>
      	<resource>
  		    <directory>src/main/resources</directory>
  	    </resource>
      	<resource>
      		<directory>src/main/java</directory>
      			<includes>
      				<include>**\\*.xml</include>
                  </includes>
  			<filtering>false</filtering>
  		</resource>
  	</resources>
  </build>
  ```

- 在 xwork-validator-1.0.dtd 中获取约束

- （type 在 `com/opensymphony/xwork2/validator/validators/default.xml`）

## 14. 拦截器

Struts2 的所有功能都是由拦截器来实现的，拦截器和过滤器相似，拦截器只过滤所有 action，并且在 Struts2 中，所有功能都是可插拔的，还可以自定义拦截器来实现一些 Struts2 没有提供的功能

拦截器是通过代理来实现的（AOP），拦截器是单例的，所有 action 共享相同的拦截器，所以拦截器中定义常量时要注意线程安全问题

| 拦截器名称        | 作用                                                         |
| ----------------- | ------------------------------------------------------------ |
| alias             | 对于 HTTP 请求包含的参数设置别名                             |
| autowiring        | 将某些 JavaBean 实例自动绑定到其它 Bean 对应的属性中         |
| chain             | 让前一个 Action 的参数可以在现有的 Action 中使用             |
| conversionError   | 从 ActionContext 中将转化类型时候发生的错误添加到 Action 中的值域错误中，在校验时经常被使用到来显示类型转换错误的信息 |
| cookie            | @Since Struts-2.0.7 可以把 Cookie 注入 Action 中可以设置的名字或值中 |
| createSession     | 自动创建一个 HTTP 的 Session，尤其是对需要 HTTP 的 Session 拦截器特别有用 |
| debugging         | 用来对在视图间传递的数据进行测试                             |
| execAndWait       | 不显式执行 Aciton，在视图上显示给用户的是一个正在等待的页面，但是 Action 在后台执行着，用于进度条开发 |
| exception         | 将异常和 Action 返回的 result 相映射                         |
| fileUpload        | 支持文件上传功能的拦截器                                     |
| i18n              | 支持国际化拦截器                                             |
| logger            | 拥有日志功能的拦截器                                         |
| modelDriven       | Action 执行该拦截器的时候，可以将 getModel() 方法得到的 result 值放入值栈中 |
| scopedModelDriven | 执行该拦截器的时候，可以从一个 scope 范围检索和存储 model 值，通过调用 setModel() 方法去设置 model 值 |
| params            | 将 HTTP 请求中包含的参数值设置到 Action 中                   |
| prepare           | 假如 Action 继承了 Preparable 接口，则会调用 prepare() 方法  |
| staticParams      | 对于在 struts.xml 文件中 Action 中的设置的参数设置到对应的 Action 中 |
| scope             | 在 session 或者 application 范围中设置 Action 状态           |
| servletCofig      | 该拦截对象提供访问包含 HttpServletRequest 和 HttpServletResponse 对象的 Map 方法 |
| timer             | 输出 Action 的执行时间                                       |
| token             | 避免重复提交的校验拦截器                                     |
| tokenSession      | 和 token 拦截器类似，它还能存储提交的数据到 session 里       |
| validation        | 运行在 action-validation.xml 文件中定义的校验规则            |
| workflow          | 在 Action 中调用 validate 校验方法，如果 Action 有错误，则返回到 input 视图 |
| store             | 执行校验功能时，该拦截器提供存储和检索 Action 的所有错误和正确信息的功能 |
| checkbox          | 视图中如果有 checkbox 存在的情况，该拦截器自动将 unchecked 的 checkbox 当作一个参数记为 false 记录下来，可以用一个隐藏的菜单来记录所有未提交的 checkbox，而且缺省 unchecked  的 checkbox 是布尔型的，如果视图中 checkbox 设置的不是布尔类型，它就会被覆盖成布尔类型的值 |
| profiling         | 通过参数来激活或不激活分析检测功能，前提是 web 项目是在开发模式下 |
| roles             | 进行权限配置的拦截器，如果登录用户拥有相应权限才能去执行某一个特定的 Action |

默认定义在 struts-default.xml 下

- timer 使用

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE struts PUBLIC
          "-//Apache Software Foundation//DTD Struts Configuration 2.5//EN"
          "http://struts.apache.org/dtds/struts-2.5.dtd">
  <struts>
      <package name="ayy" namespace="/" extends="struts-default">
          <action name="hello" class="com.ayy.action.HelloAction" method="hello">
              <interceptor-ref name="timer"/>
              <interceptor-ref name="defaultStack"/>
              <result>/index.jsp</result>
          </action>
      </package>
  </struts>
  ```

- token 使用

  ```jsp
  <%@ taglib prefix="s" uri="/struts-tags" %>
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <html>
  <head>
      <title>Save</title>
  </head>
  <body>
  <form action="./temps.jsp" method="post">
      <s:token></s:token>
      Username: <input type="text" name="userI.uname"><br/>
      Age: <input type="number" name="userI.age"><br/>
      <input type="submit" value="submit">
  </form>
  </body>
  </html>
  ```

  配置 struts.xml

  __必须配置 `<interceptor-ref name="defaultStack"/>`，否则后端获取不到前端数据__

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE struts PUBLIC
          "-//Apache Software Foundation//DTD Struts Configuration 2.5//EN"
          "http://struts.apache.org/dtds/struts-2.5.dtd">
  <struts>
      <package name="ayy" namespace="/" extends="struts-default">
          <action name="hello" class="com.ayy.action.Hello2Action" method="hello">
              <interceptor-ref name="timer"/>
              <interceptor-ref name="defaultStack"/>
              <result>/index.jsp</result>
          </action>
          <action name="save" class="com.ayy.action.UserIAction" method="save">
              <interceptor-ref name="token"/>
              <interceptor-ref name="defaultStack"/>
              <result>/temp.jsp</result>
              <result name="invalid.token">/temps.jsp</result>
          </action>
          <action name="toSave" class="com.ayy.action.UserIAction" method="toSave">
              <result>/save.jsp</result>
          </action>
      </package>
  </struts>
  ```

### 14.1 自定义拦截器

实现方式

- 实现 Interceptor 接口
- 继承 AbstractInterceptor 类（其实这个类实现了 Interceptor 接口）

当拦截器方法被调用后，需要通过 `invocation.invoke()` 调用下一个拦截器，如果没有拦截器，那么执行 action 中的方法

改返回值为拦截器的值或者结果集

登录拦截器配置

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE struts PUBLIC
        "-//Apache Software Foundation//DTD Struts Configuration 2.5//EN"
        "http://struts.apache.org/dtds/struts-2.5.dtd">
<struts>
    <package name="ayy" namespace="/" extends="struts-default">
        <interceptors>
            <interceptor name="myInterceptor" class="com.ayy.interceptor.MyInterceptor"/>
            <interceptor name="loginInterceptor" class="com.ayy.interceptor.LoginInterceptor"/>
        </interceptors>

        <global-results>
            <result name="login">/login.jsp</result>
        </global-results>

        <action name="hello" class="com.ayy.action.Hello2Action" method="hello">
            <interceptor-ref name="timer"/>
            <interceptor-ref name="loginInterceptor"/>
            <result>/index.jsp</result>
        </action>
        <action name="login" class="com.ayy.action.UserAction" method="login">
            <interceptor-ref name="token"/>
            <interceptor-ref name="loginInterceptor"/>
            <interceptor-ref name="defaultStack"/>
            <result>/temps.jsp</result>
            <result name="invalid.token">/index.jsp</result>
        </action>
        <action name="toLogin" class="com.ayy.action.UserAction" method="toLogin">
            <interceptor-ref name="myInterceptor"/>
            <result>/login.jsp</result>
        </action>
        <action name="save" class="com.ayy.action.UserAction" method="save">
            <interceptor-ref name="token"/>
            <interceptor-ref name="loginInterceptor"/>
            <interceptor-ref name="defaultStack"/>
            <result>/temps.jsp</result>
            <result name="invalid.token">/index.jsp</result>
        </action>
        <action name="toSave" class="com.ayy.action.UserAction" method="toSave">
            <interceptor-ref name="myInterceptor"/>
            <interceptor-ref name="loginInterceptor"/>
            <result>/save.jsp</result>
        </action>
    </package>
</struts>
```

### 14.2 拦截器栈和方法拦截器

拦截器栈

```xml
<interceptors>
    <interceptor name="myInterceptor" class="com.ayy.interceptor.MyInterceptor"/>
    <interceptor name="loginInterceptor" class="com.ayy.interceptor.LoginInterceptor"/>
    <interceptor-stack name="myStack">
        <interceptor-ref name="timer"/>
        <interceptor-ref name="myInterceptor"/>
        <interceptor-ref name="loginInterceptor"/>
    </interceptor-stack>
</interceptors>
```

设定默认拦截器

```xml
<default-interceptor-ref name="myStack"/>
```

方法拦截器：继承 `MethodFilterInterceptor` 

```xml
<interceptor-ref name="methodInterceptor">
    <param name="includeMethods">hello,toSave</param>
    <param name="excludeMethods"/>
</interceptor-ref>
```

### 14.3 内置拦截器的执行方式

ModelDriven 模型驱动内部实现

```java
@Override
public String intercept(ActionInvocation invocation) throws Exception {
    Object action = invocation.getAction();
	// 判断类是否实现了 ModelDriven 的接口
    if (action instanceof ModelDriven) {
        ModelDriven modelDriven = (ModelDriven) action;
        ValueStack stack = invocation.getStack();
        Object model = modelDriven.getModel();
        if (model !=  null) {
            stack.push(model);
        }
        if (refreshModelBeforeResult) {
            invocation.addPreResultListener(new RefreshModelBeforeResult(modelDriven, model));
        }
    }
    return invocation.invoke();
}
```

### 14.4 文件上传

- JSP 页面表单必须是 post 提交，并且设置 `enctype="multipart/form-data"` 

- Action 中属性声明为

  - 文件：类型为 File，名称与 form 表单中的名称相匹配

  - 文件名：类型为 String，名称为__表单属性名__+`FileName` 

  - 文件类型：类型为 String，名称为__表单属性名__+`ContentType` 

    ```java
    String contentTypeName = inputName + "ContentType";
    String fileNameName = inputName + "FileName";
    ```

- 可以在 `server.xml` 配置路径

  ```xml
  <Context path="/fileupload" docBase=".../WEB-INF" reloadable="true"/>
  ```

- 上传大文件

  ```xml
  <constant name="struts.multipart.maxSize" value="67288165"/>
  <package name="ayy" namespace="/" extends="struts-default">
      <action>
          <interceptor-ref name="fileUpload">
              <param name="maximumSize">67288165</param>
          </interceptor-ref>
      </action>
  </package>    
  ```

- 配置临时目录

  ```xml
  <constant name="struts.multipart.saveDir" value="c:\"/>
  ```

- 多文件上传将 Action 中接收的文件对象改成数组对象

### 14.5 文件下载

不安全的方式，无权限控制，会直接打开可以打开的文件

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Download</title>
</head>
<body>
<a href="./download/codeassist.txt">Download</a><br/>
</body>
</html>
```

安全方式：

Servlet 中

- 设置类型为 `application/octet-stream` 或 `application/x-msdownload` 

- 设置 Header `resp.setHeader("Content-Disposition","attachment;filename=\"filename\"");` 

  ```java
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletExceptionm IOException {
      String path = req.getRealPath("/download");
      File file = new File(path,"filename.txt");
      resp.setContentType("application/octet-stream");
      resp.setHeader("Content-Disposition","attachment;filename=\"filename.txt\"");
      resp.setContentLength((int)file.length());
      
      InputStream is = new FileInputStream(file);
      byte[] buffer = new byte[400];
      int len = -1;
      while((len = is.read(buffer))!=-1) {
          resp.getOutputStream().write(buffer, 0, len);
      }
      is.close();
      resp.getOutputStream().close();
  }
  ```

Struts 中：

- 配置 DownloadAction 

  ```java
  public class DownloadAction {
      private String fileName;
  
      public String execute(){
          return Action.SUCCESS;
      }
  
      public InputStream getInputStream(){
          String path = ServletActionContext.getServletContext().getRealPath("/download");
          try {
              return new FileInputStream(new File(path,fileName));
          } catch (FileNotFoundException e) {
              e.printStackTrace();
          }
          return null;
      }
  
      public String getFileName() {
          return fileName;
      }
  
      public void setFileName(String fileName) {
          this.fileName = fileName;
      }
  }
  ```

- 配置 struts.xml 

  ```xml
  <action name="download" class="com.ayy.action.DownloadAction">
      <result type="stream">
      	<param name="contentDisposition">attachment;fileName=${fileName}</param>
          <param name="inputName">xxx</param>
      </result>
  </action>
  ```

- 配置 jsp

  ```jsp
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <html>
  <head>
      <title>Download</title>
  </head>
  <body>
  <a href="download.action?fileName=codeassist.txt">Download</a><br/>
  </body>
  </html>
  ```

## 15. Ajax

__Struts2 中可以使用 Servlet 的 Ajax__ 

JSP 页面

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <script type="text/javascript" src="js/jquery-3.5.1.js"></script>
    <script type="text/javascript">
        $(function (){
           $("#uname").blur(function (){
               $.post("checkName.action",{"username":$(this).val()},function (data) {
                   if("true"==data){
                       $("#uname").css("border","1px solid red");
                   }else {
                       $("#uname").css("border","1px solid green");
                   }
               });
           });
        });
    </script>
</head>
```

Action 类

```java
public class AjaxAction {
    private String username;

    public String checkName() throws IOException {
        HttpServletResponse resp = ServletActionContext.getResponse();
        if("USR1".equals(username)){
            resp.getWriter().print("true");
        }else {
            resp.getWriter().print("false");
        }
        return null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
```

struts.xml

```xml
<action name="checkName" class="com.ayy.action.AjaxAction" method="checkName"/>
```

__Struts2 自己使用__ 

- jar 包

  ```xml
  <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.12.1</version>
  </dependency>
  <dependency>
      <groupId>com.hynnet</groupId>
      <artifactId>json-lib</artifactId>
      <version>2.4</version>
  </dependency>
  <dependency>
      <groupId>org.apache.struts</groupId>
      <artifactId>struts2-json-plugin</artifactId>
      <version>2.5.26</version>
  </dependency>
  ```

- JSP

  ```jsp
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <html>
  <head>
      <title>User List Ajax</title>
      <script type="text/javascript" src="js/jquery-3.5.1.js"></script>
      <script type="text/javascript">
          $(function () {
              $("#btn").click(function (){
                  $.post("list.action",function (data) {
                      let html="";
                      for (let i=0; i<data.length; i++){
                          html+="<tr>";
                          html+="<td>";
                          html+=data[i].uid;
                          html+="</td>";
                          html+="<td>";
                          html+=data[i].uname;
                          html+="</td>";
                          html+="<td>";
                          html+=data[i].age;
                          html+="</td>";
                          html+="</tr>"
                      }
                      $("#content").html(html);
                  },"json");
              });
          });
      </script>
  </head>
  <body>
  <button id="btn">Get List</button>
  <table width="80%" align="center">
      <thead>
      <tr>
          <td>UID</td>
          <td>UNAME</td>
          <td>AGE</td>
      </tr>
      </thead>
      <tbody id="content"></tbody>
  </table>
  </body>
  </html>
  ```

- Action 类

  ```java
  public class JsonAction {
      private List<User> userList;
  
      public String list(){
          userList = new ArrayList<>();
          userList.add(new User(1,"USR1",21));
          userList.add(new User(2,"USR2",22));
          userList.add(new User(3,"USR3",23));
          userList.add(new User(4,"USR4",24));
          return Action.SUCCESS;
      }
  
      public List<User> getUserList() {
          return userList;
      }
  
      public void setUserList(List<User> userList) {
          this.userList = userList;
      }
  }
  ```

- struts.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE struts PUBLIC
          "-//Apache Software Foundation//DTD Struts Configuration 2.5//EN"
          "http://struts.apache.org/dtds/struts-2.5.dtd">
  <struts>
      <package name="ayy" namespace="/" extends="json-default">
          <action name="list" class="com.ayy.action.JsonAction" method="list">
              <result type="json">
                  <param name="root">userList</param>
              </result>
          </action>
      </package>
  </struts>
  ```

## 16. 异常处理

struts.xml 配置

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE struts PUBLIC
        "-//Apache Software Foundation//DTD Struts Configuration 2.5//EN"
        "http://struts.apache.org/dtds/struts-2.5.dtd">
<struts>
    <package name="ayy" namespace="/" extends="struts-default">
        <global-exception-mappings>
            <exception-mapping exception="com.ayy.exception.UserException" result="userexcep"/>
        </global-exception-mappings>
        <action name="delete" class="com.ayy.action.UserAction" method="delete">
            <!--            <exception-mapping exception="com.ayy.exception.UserException" result="userexcep"/>-->
            <result>/success.jsp</result>
            <result name="userexcep">/error.jsp</result>
        </action>
    </package>
</struts>
```

在 Action 中只要抛出对应异常即可