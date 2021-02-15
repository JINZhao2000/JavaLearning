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

- 在 xwork-validator-1.0.dtd 中获取约束

- （type 在 `com/opensymphony/xwork2/validator/validators/default.xml`）



