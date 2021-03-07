# SpringMVC

最好过一下 Struts2 的知识，其实差不多的

## 1. SpringMVC Hello World

- Java 类（Controller）

  ```java
  public class HelloController implements Controller {
      @Override
      public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
          ModelAndView mv = new ModelAndView();
          mv.addObject("msg","HelloSpringMVC");
          mv.setViewName("hello");
          return mv;
      }
  }
  ```

- Spring 配置文件 springmvc-servlet.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd">
      <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"/>
      <bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter"/>
      <bean id="InternalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
          <property name="prefix" value="/WEB-INF/jsp/"/>
          <property name="suffix" value=".jsp"/>
      </bean> <!-- /WEB-INF/jsp/*.jsp -->
      <bean id="/hello" class="com.ayy.controller.HelloController"/>
  </beans>
  ```

- web.xml

  ```xml
  <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
           http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
           version="4.0">
      <servlet>
          <servlet-name>springmvc</servlet-name>
          <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
          <init-param>
              <param-name>contextConfigLocation</param-name>
              <param-value>classpath:springmvc-servlet.xml</param-value>
          </init-param>
          <load-on-startup>1</load-on-startup>
      </servlet>
  
      <servlet-mapping>
          <servlet-name>springmvc</servlet-name>
          <url-pattern>/</url-pattern>
          <!-- 
   			/  只匹配所有请求 不匹配 jsp
  			/* 匹配所有请求和 jsp
  		-->
      </servlet-mapping>
  </web-app>
  ```

- jsp

  ```jsp
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <html>
  <head>
      <title>Hello</title>
  </head>
  <body>
  ${msg}
  </body>
  </html>
  ```

- SpringMVC 执行流程

  <img src="./images/mvc.png">

  - Incoming request 发送请求
  - Front controller 前端控制器（Dispatcher Servlet）
  - Delegate request 委托请求给处理器
  - Controller 页面控制器 / 处理器
  - Handle request
  - Create Model
  - Delegate rendering of response 返回 ModelAndView
  - Render response 渲染视图
  - View Template 视图
  - Return control 返回控制
  - Return response 产生响应
  - --- 接收用户的请求 --> DispatcherServlet --- 处理器映射，根据 url 查找 Handler --> HandlerMapping --- Execution 表示具体 Handler --> HandlerExecution --- 将解析信息传回 --> DispatcherServlet --- 处理器适配器，按特定规则执行 Handler --> HandlerAdapter --- Handler 让具体 Controller 执行 --> Contoller --- 将信息回传 --> HandlerAdapter --- 将视图逻辑名或模型传递给 DispatcherServlet --> DispatcherServlet--- 调用视图解析器解析 Adapter 的视图逻辑名 --> ViewResolver --- 将解析的逻辑视图名传给 DispatcherServlet --> DispatcherServlet --- 根据视图解析器结果调用具体的视图 --> View --- 返回给用户 -->
  - __Contoller <-> Service (Business Logic)__ <-> Repository (Data Access) <-> MySQL
  - View <-> __Model <-> Controller__ 
  - __View <-> ViewResolver__ 

## 2. 注解开发

- Java 类（Controller）

  ```java
  @Controller
  public class HelloController {
      @RequestMapping("/hello") // 请求 url
      public String hello(Model model){
          model.addAttribute("msg","HelloSpringMVC");
          return "hello"; // jsp 文件名
      }
  }
  ```

- Spring 配置

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:context="http://www.springframework.org/schema/context"
         xmlns:mvc="http://www.springframework.org/schema/mvc"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/context
          http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd">
  
      <context:component-scan base-package="com.ayy"/>
      <mvc:default-servlet-handler/>
      <mvc:annotation-driven/>
  
      <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
          <property name="prefix" value="/WEB-INF/jsp/"/>
          <property name="suffix" value=".jsp"/>
      </bean>
  </beans>
  ```

## 3. Restful

- 参数传递

  ```java
  @Controller
  public class RestfulController {
      @RequestMapping(value = "/add/{a}/{b}")
      // @GetMapping()
      // @DeleteMapping()
      // @PostMapping()
      // @PutMapping()
      // @RequestMapping()
      public String rest1(@PathVariable int a,@PathVariable int b, Model model){
          model.addAttribute("msg","Result: "+(a+b));
          return "hello";
      }
  }
  ```

## 4. 结果跳转方式

- Servlet API 中的跳转方式

  ```java
  resp.sendRedirect("...");
  resp.getRequestDispatcher("...").forward(req, resp);
  ```

- Spring 的默认是转发

  ```java
  return "forward:xxx";
  return "redirect:/xxx.jsp";
  ```

- 接收 url 参数

  ```java
  @Controller
  @RequestMapping("/user1")
  public class UserController {
      @RequestMapping("/test1")
      public String test1(@RequestParam("uname") String name, Model model){
          System.out.println(name);
          model.addAttribute("msg",name);
          return "hello";
      }
  
      @RequestMapping("/test2")
      public String test2(User user){
          System.out.println(user);
          return "hello";
      }
  }
  ```

- 乱码解决

  ```xml
  <filter>
      <filter-name>encoding</filter-name>
      <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
      <init-param>
          <param-name>encoding</param-name>
          <param-value>utf-8</param-value>
      </init-param>
  </filter>
  <filter-mapping>
      <filter-name>encoding</filter-name>
      <url-pattern>/*</url-pattern>
  </filter-mapping>
  ```

## 5. JSON

- js 对象转 json

  ```javascript
  let user = {
              name:"user",
              age:18,
              sex:"N"
          };
  let userJson = JSON.stringify(user);
  ```

- js json 转对象

  ```javascript
  let obj = JSON.parse(userJson);
  ```

- java 与 json（Jackson）

  - 导入 jackson 包

    ```xml
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.12.2</version>
    </dependency>
    ```

  - jackson 乱码问题

    方案一：produces

    ```java
    @RequestMapping(value = "/json1",produces = "application/json;charset=utf-8")
    ```

    方案二：spring 配置 jackson

    ```xml
    <mvc:annotation-driven>
        <mvc:message-converters>
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <constructor-arg value="UTF-8"/>
            </bean>
            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                <property name="objectMapper">
                    <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                        <property name="failOnEmptyBeans" value="false"/>
                    </bean>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>
    ```

  - controller

    ```java
    @RestController
    public class Xxx{
        
    }
    
    @Controller
    public class Xxx{
        @RequestMapping("...")
        @RequestBody
        public String xxx(){
            
        }
    }
    ```

  - 转换为 json

    ```java
    @RequestMapping("/json1")
    public String json1() throws JsonProcessingException {
        User user = new User("user1", 18, "n");
        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(user);
        return userJson;
    }
    
    @RequestMapping("/json2")
    public String json2() throws JsonProcessingException {
        User user1 = new User("user1", 18, "n");
        User user2 = new User("user2", 18, "n");
        User user3 = new User("user3", 18, "n");
        User user4 = new User("user4", 18, "n");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
    
        return new ObjectMapper().writeValueAsString(users);
    }
    
    @RequestMapping("/json3")
    public String json3() throws JsonProcessingException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new ObjectMapper().writeValueAsString(sdf.format(date));
    }
    
    @RequestMapping("/json4")
    public String json4() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(sdf);
        Date date = new Date();
        return mapper.writeValueAsString(date);
    }
    ```

- Fastjson


  - 导入 jar

    ```xml
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>fastjson</artifactId>
        <version>1.2.75</version>
    </dependency>
    ```

  - 使用

    ```java
    JSON.toJSONString(users);
    ```

    

