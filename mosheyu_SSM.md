墨折羽
# SSM学习第一章节——Spring入门
* * *
学习SSM的第一章，主要是Spring的入门，搭建项目，导包（使用了maven），配置文件。
## Bean的取值范围：
* * *
1. singleton：默认值，单例的。spring容器启动时就构建唯一的Bean。
2. prototype：多例的。使用时构建。
3. ~~

## Bean生命周期配置
* * *
1. init-method:指定类的初始化方法
2. destroy-method:指定类的销毁方法

## Bean的三种实例化方式：
* * *
1. 无参构造方法实例化
2. 工厂静态方法实例化
3. 工厂实例方法实例化

## Bean依赖注入：把某个类所需要的形参，属性传到这个类中。
* * *
注入方式：
1. setter注入
    1. 使用bean的子标签<property>根据name确定setter方法，然后选择ref注入引用，或者使用value注入值
    2. 使用命名空间，调用同处于spring容器中的bean。
    
2. 构造方法注入，给bean构造方法传递值。
# SSM学习第二章节——Spring配置数据源
* * *
## spring配置数据源
* * *
数据源，即数据库连接池。
常见的数据源：DBCP、C3P0、BoneCP、Druid等。
### 开发步骤：
* * *
1. 导包，导入数据源的坐标和数据库驱动坐标。
2. 创建，创建数据源对象。
3. 设置，设置数据源的基本连接数据，账号密码等。
4. 使用及归还，使用数据源获取连接资源和归还连接资源。

### 加载配置文件
* * *
applicationContext.xml加载jdbc.properties配置文件获取信息，jdbc.properties此处用来存储数据库的值。
引入命名空间和约束路径
~~~
xmlns:context="http://www.springframework.org/schema/context"
 
http://www.springframework.org/schema/context
http://www.springframework.org/schema/context/spring-context.xsd
~~~
配置文件使用举例：
~~~xml
<beans>
    <context:property-placeholder location="classpath:jdbc.properties"/>
    <bean id="datasSourse" class="com.mchange.v2.c3p0.ComboPooledDataSource">
         <property name="driverClass" value="${jdbc.driver}"></property>
         <property name="jdbcUrl" value="${jdbc.url}"></property>
         <property name="user" value="${jdbc.username}"></property>
         <property name="password" value="${jdbc.password}"></property>
    </bean>
</beans>
~~~
## spring注解开发
* * *
### 原始注解
* * *
|注解| 说明 |
| --- | ---|
|@Component|在类上使用，实例化Bean。|
|@Controller|使用在web层上实例化Bean。|
|@Service|使用在service层上实例化Bean。|
|@Repository|使用在dao层上实例化Bean。|
|@Autowired|在普通数据类型上使用，根据类型进行依赖注入。|
|@Qualifier|与@Autowired一起使用，根据名称进行依赖注入。|
|@Resource|相当于@Autowired+@Qualifier，根据名称进行注入。|
|@Value|注入普通属性，可以指定值，以及spring容器，命名空间的值。|
|@Scope|标注Bean的作用范围，比如单例和多例。|
|@PostConstruce|在方法上使用，标注Bean的初始化方法。|
|@PreDestroy|在方法上使用，标注Bean的销毁方法。|
### 新注解
* * *
|注解| 说明 |
| --- | ---|
|@Configuration|指定当前类是一个Spring配置类，创建容器时会在该类上加载注解。|
|@ComponentScan|指定Spring在初始化容器要扫描的包。|
|@Bean|使用在方法上，将方法的返回值存储到spring容器中。|
|PropertySOurce|加载.properties文件中的配置。|
|@import|导入其他配置类。|
## spring集成Junit
* * *
步骤：
1. 导入spring集成Junit的坐标。
2. 使用@Runwith注解替换原来的运行期
3. 使用@ContextConfiguration指定配置文件或配置类。
4. 使用@Autowired注入需要测试的对象。
5. 使用@Test创建测试方法进行测试

注意：SpringJUnit4ClassRunner 需要搭配 4.12 及以上版本的 Junit 才可以执行。
# SSM学习第三章节——SpringMVC
* * *
## 配置Spring的Web环境
* * *
配置servlet，继承HttpServlet类，或者实现接口。  
### 原始配置
* * *
在web.xml中配置servlet。
web中代码如下：
~~~xml
<web-app>
   <servlet>
        <servlet-name>UserServlet</servlet-name>
        <servlet-class>com.mosheyu.web.UserServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>UserServlet</servlet-name>
        <url-pattern>/userServlet </url-pattern>
    </servlet-mapping>
</web-app>
~~~
一般应用上下文对象是通过`new ClassPathXmlApplicationContext("配置文件")`方式获取到的。

servlet应用监听器`ServletContextListener`获取应用上下文。避免为每一个Servlet编写一个new。

过程：在Web应用启动时，就加载配置文件并创建应用上下文对象`ApplicationContext`，将对象存储到最大的域`servletContext`域中，这样就避免了大量的编写new来获取上下文对象。

举例：
创建监听器，创建应用上下文并存储到ServletContext域中。
```java
public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("app",app);
    }
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
```
web.xml中配置监听器。
```xml
   <listener>
        <listener-class>com.mosheyu.listener.ContextLoaderListener</listener-class>
    </listener>
```
servlet中获取应用上下文
```java
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        ServletContext servletContext = req.getServletContext();
//        ServletContext servletContext = this.getServletContext();
        ApplicationContext app = (ApplicationContext)servletContext.getAttribute("app");
        UserService userService = app.getBean(UserService.class);
        userService.out();
    }
}
```
### 优化配置一
* * *
此处为了将配置文件名存储到配置文件中，方便之后修改，将配置文件名配置到web.xml中作为全局参数。然后使用时从web.xml中获取。

举例：
```xml
    <!-- 配置全局化参数   -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>applicationContext.xml</param-value>
    </context-param>
```
监听器修改。
```java
public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //将Spring的应用上下文对象，存储到ServletContext域中。
        ServletContext servletContext = servletContextEvent.getServletContext();
        //读取web.xml中的参数
        String contextConfigLocation = servletContext.getInitParameter("contextConfigLocation");
        ApplicationContext app = new ClassPathXmlApplicationContext(contextConfigLocation);
        servletContext.setAttribute("app",app);
    }
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
```
### 优化配置二
* * *
此时获取获取应用上下文还需要记住ApplicationContext在ServletContext域中的别名，所以单独的写了一个静态工具方法，返回应用上下文对象。在使用时，调用相应的方法即可获取。
```
    public static ApplicationContext getApplicationContext(ServletContext servletContext){
        return (ApplicationContext)servletContext.getAttribute("app");
    }
```
### 配置Spring自带的监听器
* * *
上述是spring获取应用上下文的流程，在实际开发中，spring已经提供了相应的监听器`ContextLoaderListener`。在监听器内部来加载Spring配置文件，然后提供客户端工具类`WebApplicationContextUtils`来获取应用上下文对象。

两步：
1. 在web.xml中配置ContextLoaderListener监听器(导入spring-web坐标)。
2. 使用WebApplicationContextUtils获取应用上下文对象ApplicationContext使用。

步骤：
添加新的包
```xml
  <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>5.0.5.RELEASE</version>
  </dependency>
```
web配置
```xml
<web-app>
   <!-- 全局变量  -->
   <context-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:applicationContext.xml</param-value>
   </context-param>
   <!-- 监听器  -->
   <listener>
      <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
   </listener>
</web-app>
```
servlet使用
```java
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        ServletContext servletContext = this.getServletContext();
        ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        UserService userService = app.getBean(UserService.class);
        userService.out();
    }
}
```
## 配置SpringMVC
* * *
开发步骤：
1. 导入SpringMVC包。
2. 配置SpringMVC的核心控制器DispathcerServlet，用来配置Servlet的共有部分。
3. 创建各个功能的Servlet。
4. 将Servlet配置到Spring容器中。并且配置映射地址。
5. 配置SpringMVC的核心文件spring-mvc.xml（一般命名为此），配置包扫描等。
6. 使用测试。

具体操作步骤：
1.导包。
```xml
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.0.5.RELEASE</version>
        </dependency>
```
2.配置SpringMVC的前端控制器。
```xml
<web-app>
   <servlet>
      <servlet-name>DispatcherServlet</servlet-name>
      <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
      <load-on-startup>1</load-on-startup>
   </servlet>
   <servlet-mapping>
      <servlet-name>DispatcherServlet</servlet-name>
      <url-pattern>/</url-pattern>
   </servlet-mapping>

</web-app>
```
3.创建自定义Servlet。
```java
public class UserController {
    public String out(){
        System.out.println("控制器执行了。");
        return "testSpringMVC.jsp";
    }
}

```
4.配置映射地址。
```java
@Controller
public class UserController {
    
    @RequestMapping("/quick")
    public String out(){
        System.out.println("控制器执行了。");
        return "testSpringMVC.jsp";
    }
}
```
5.配置SpringMVC的核心文件spring-mvc.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                            http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-beans.xsd
        ">
    <!--  Controller组件扫描      -->
<!--    <context:component-scan base-package="com.mosheyu.controller"/>-->
    
    
</beans>
```
使用servlet的子元素init-param来为DispatcherServlet配置使用此配置文件。
```xml
<web-app>
   <!-- 配置SpringMVC的前端控制器  -->
   <servlet>
      <servlet-name>DispatcherServlet</servlet-name>
      <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
      <init-param>
         <param-name>contextConfigLocation</param-name>
         <param-value>classpath:spring-mvc.xml</param-value>
      </init-param>
      <load-on-startup>1</load-on-startup>
   </servlet>
   <servlet-mapping>
      <servlet-name>DispatcherServlet</servlet-name>
      <url-pattern>/</url-pattern>
   </servlet-mapping>
</web-app>
```
## SpringMVC的组件解析
* * *
###SpringMVC的相关组件
* * *
|组件|名称|
|---|---|
|前端控制器|DispathcherServlet|
|处理器映射器|HandlerMapping|
|处理器适配器|HandlerAdapter|
|处理器|Handler|
|视图解析器|View Resolver|
|视图|view|
### SpringMVC执行流程：
* * *
1. 客户端发送请求到前端控制器DispatcheServlet。
2. 前端控制器收到请求调用HandleMapping处理映射器。
3. 处理映射器找到具体的处理器，生成器对象以及处理器拦截器一并返回给前端控制器。
4. 前端控制器调用HandleAdapter处理器适配器。
5. HandleAdapter经过适配调用具体的处理器（后端控制器，即自定义的Servlet）。
6. 后端控制器返回ModelAndView。
7. HandleAdapter将返回过来的ModelAndView返回给前端控制器。
8. 前端控制器将ModelAndView传给ViewReslove视图解析器。
9. ViewReslove视图解析器解析后返回具体的View（jsp页面）。
10. 前端控制器根据View进行渲染视图（将数据填充到视图中），前端控制器相应客户端。

### SpringMVC注解解析：
* * *
**@RequestMapping**:请求映射，建立请求url和处理请求方法之间建立对应关系。
属性：

|属性|作用|
|---|---|
|value|指定请求的URL.|
|mathod|指定请求的方式。指get，post等方法，使用现成的枚举类型。例：<br />`method=RequestMethod.GET`|
|params|指定限制请求参数的条件，支持简单的表达式，要求请求参数的key和value必须和配置的一致。例：<br />1. `params={"name"}`指定参数必须有name参数。<br />2. `params={"moeny!100"}`指定请求参数中moeny不能是100.|
# SSM学习第四章节——SpringMVC具体应用
* * *
## SpringMVC的响应方式
* * *
1. 页面跳转：
   1. 直接返回字符串 
   2. 通过ModelAndView对象返回。
2. 回写数据：
   1. 直接返回字符串
   2. 返回对象或集合。
   
### 页面跳转：
* * *
#### 直接返回字符串形式
* * *
直接返回字符串：将返回的字符串与视图解析器的前后缀拼接后跳转。
在xml文件中手动配置内部资源视图解析器，配置内部属性前后缀。
```xml
    <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/jsp/"></property>
        <property name="suffix" value=".jsp"></property>
    </bean>
```
使用时可以省略前后缀。
```java
@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest")
   public String out() {
      return "/test";
   }
}
```
#### ModelAndView对象
* * *
通过ModelAndView对象返回。有多种方式。
代码举例：  
##### 手动创建ModelAndView并返回。
* * *
```java
@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest2")
   public ModelAndView out2(){
        /*
            Model 模型，用来封装数据。
            View  视图，用来展示数据。
         */
      ModelAndView modelAndView = new ModelAndView();
      //设置视图名
      modelAndView.setViewName("test");
      //设置模型数据
      modelAndView.addObject("name","out2");
      return modelAndView;
   }
}
```
##### Spring自动注入ModelAndView
* * *
设置形参为ModelAndView，Spring会自动注入。
```java
@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest3")
   public ModelAndView out3(ModelAndView modelAndView){
      modelAndView.setViewName("test");
      modelAndView.addObject("name","out3");
      return modelAndView;
   }
}
```
##### Spring自动注入Model
* * *
设置形参为Model，Spring也会自动注入。
```java
@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest4")
   public String out4(Model model){
      model.addAttribute("name","out4");
      return "test";
   }
}
```
##### 原始request方式
* * *
采用原始的request方式，设置形参为HttpServletRequest，Spring会自动注入。但是使用框架时建议少用。
```java
@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest5")
   public String out5(HttpServletRequest request){
      request.setAttribute("name","out5");
      return "test";
   }

}
```
###  回写数据
* * *
#### 直接返回字符串
* * *
在jsp中，如果回写字符串作为响应体返回，使用代码`response.getWriter().print("字符串")`即可。

在框架中，有多种方式。
#####  SpringMVC注入response形参
* * *
通过SpringMVC框架注入response形参，然后使用同样的代码`response.getWriter().print("字符串")`回写数据，此时不用实体跳转，方法返回值为void。
```java
@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest6")
   public void out6(HttpServletResponse response) throws IOException {
      response.getWriter().println("test能否使用");
   }
}
```
#####  `@ResponBody`注解
* * *
为了框架与前端解耦合，提供了另一种方式。将需要回写的字符串直接返回，但是需要使用`@ResponBody`注解来注明，方法返回的字符串不是跳转，而且直接在Http响应体中返回。
```java
@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest7")
   @ResponseBody
   public String out7() throws IOException {
      return  "test能否使用";
   }
}
```
举例：回写json格式字符串。
简单直接的写法：
```java
@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest8")
   @ResponseBody
   public String out8() throws IOException {
      return  "{\"name\":\"out8,\"age\":\"2\"}";
   }
}
```
但是在编写json字符串时，仍然很麻烦，所以使用json工具类来转换。
导包：
```xml
 <dependencies>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-core</artifactId>
            <version>2.9.0</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.9.0</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-annotations</artifactId>
            <version>2.9.0</version>
        </dependency>
</dependencies>
```
具体使用。
```java
@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest9")
   @ResponseBody
   public String out9() throws IOException {
      User user = new User();
      user.setName("mosheyu");
      user.setAge(99);
      //使用json转换工具将对象转为json字符串返回。
      ObjectMapper objectMapper = new ObjectMapper();
      String json  = objectMapper.writeValueAsString(user);
      return  json;
   }

}
```
#### 返回对象或集合。
* * *
上述方法虽然也可以，但是还是比较繁琐。Spring提供了简化的方法。

##### SpringMVC配置json转换并回写
* * *
通过SpringMVC来将对象或集合进行json字符串的转换并回写，为处理器适配器配置消息转换参数，指定使用jackson进行对象或集合的转换。  
xml文件配置如下：
```xml
<!--    配置处理器映射器-->
    <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter">
        <property name="messageConverters">
            <list>
                <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter"></bean>
            </list>
        </property>
    </bean>
```
在servlet中使用：
```java
@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest11")
   @ResponseBody
   public User out11() throws IOException {
      User user = new User();
      user.setName("mosheyu");
      user.setAge(11);
      return  user;
   }
}
```
##### SpringMVC注解
* * *
上述方式可以返回对象或集合，但是还是比较繁琐，可以使用mvc的注解来替代上文。
```xml
<mvc:annotation-driver/>
```
在SpringMVC的组建中，**处理器映射器**、**处理器适配器**、**视图解析器**是三大组件。
使用代码`<mvc:annotation-driver/>`可以自动加载**RequestMappingHandleMapping(处理器映射器)**和**RequestMappingHandlerAdapter(处理器适配器)**。  
可以用代码`<mvc:annotation-driver/>`来替代**注解处理器**和**适配器**的配置。  
同时代码`<mvc:annotation-driver/>`默认底层自带的集成了jackson进行对象或集合的json格式字符串的转换。  
xml配置文件：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/mvc
       http://www.springframework.org/schema/mvc/spring-mvc.xsd">
<!--    MVC的注解驱动-->
    <mvc:annotation-driven/>
</beans>
```
## SpringMVC获得请求数据
* * *
### 获得请求参数
* * *
参数类型：
1. 基本类型参数
2. POJO类型参数（javaBean）
3. 数组类型参数
4. 集合类型参数    

#### 获取基本数据类型参数
* * *
Controller中的业务方法的参数名称与请求参数的name一致，参数值会自动映射匹配。   
举例：    
地址访问
```
http://localhost:8080/acpt03/user/attest12?name=墨折羽&age=12
```
代码实现：
```java
@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest12")
   @ResponseBody
   public void out12(String name,int age) throws IOException {
      System.out.println(name);
      System.out.println(age);
   }
}
```
#### 获取POJO类型参数
* * *
Controller中的业务方法的POJO参数的属性名与请求参数的name一致，参数值会自动映射匹配。  
举例：  
地址访问
```
http://localhost:8080/acpt03/user/attest13?name=墨折羽&age=13
```
POJO(javaBean)
```java
public class User {
        String name;
        Integer age;
//      getter、setter
}
```
servlet使用：
```java
@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest13")
   @ResponseBody
   public void out13(User user) throws IOException {
      System.out.println(user);
   }
}
```
#### 获取数组类型参数
* * *
Controller中的业务方法数组名称与请求参数的name一致，参数值会自动映射匹配。  
举例：  
地址访问
```
http://localhost:8080/acpt03/user/attest14?strs=111&strs=222&strs=333
```
servlet使用：
```java
@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest14")
   @ResponseBody
   public void out14(String[] strs) throws IOException {
      System.out.println(Arrays.asList(strs));
   }
}
```
#### 获取集合类型参数
* * *
##### 手动封装POJO
* * *
获取集合参数时，要将集合参数包装到一个POJO中才可以。  
举例：  
地址访问
```
http://localhost:8080/acpt03/jsp/listTest.jsp
```
封装对象：
```java
public class VO {
   private List<User> userList;

   public List<User> getUserList() {
      return userList;
   }

   public void setUserList(List<User> userList) {
      this.userList = userList;
   }

   @Override
   public String toString() {
      return "VO{" +
              "userList=" + userList +
              '}';
   }
}
```
servlet使用：
```java
@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest15")
   @ResponseBody
   public void out15(VO vo) throws IOException {
      System.out.println(vo.toString());
   }
}
```
##### Spring自动封装POJO
* * *
重点在于前端的使用：  
name的值为集合的元素下标加属性名。Spring会自动帮我们装到之前创建的POJO对象中，然后作为一个形参传递过去。
```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/user/attest15" method="post">
        第一个用户的姓名：<input type="text" name="userList[0].name"/><br />
        第一个用户的年龄：<input type="text" name="userList[0].age"/><br />
        第二个用户的姓名：<input type="text" name="userList[1].name"/><br />
        第二个用户的年龄：<input type="text" name="userList[1].age"/><br />
        第三个用户的姓名：<input type="text" name="userList[2].name"/><br />
        第三个用户的年龄：<input type="text" name="userList[2].age"/><br />
        <input type="submit" value="提交">
    </form>
</body>
</html>
```
##### 注解封装
* * *
当前端使用ajax提交时，可以直接指定contextType为json形式，可以在方法的形参上加注解`@RequestBody`直接注入数据，省略创建一个包装对象。  
前端ajax请求(导入jQuery包）：  
```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
    <script>
        var userList = new Array();
        userList.push({name:"one",age:18});
        userList.push({name:"two",age:20});
        userList.push({name:"three",age:30});
        $.ajax({
            type:"post",
            url:"${pageContext.request.contextPath}/user/attest16",
            data:JSON.stringify(userList),
            contentType:"application/json;charset=utf-8"
        });
    </script>
</head>
<body>

</body>
</html>
```
访问地址：  
```
http://localhost:8080/acpt03/ajax.jsp
```
##### 配置静态文件jQuery
* * *  
```xml
<bean>
   <mvc:resources mapping="/js/**" location="/js/"/>
</bean>
```
servlet使用：  
```java
@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest16")
   @ResponseBody
   public void out16(@RequestBody List<User> userList) throws IOException {
      System.out.println(userList.toString());
   }
}
```
##### 开放资源的访问。
* * *
前端匹配前端文件jQuery.js文件时，spring也会当做一个请求，在servlet中寻找对应的文件，结果当然是找不到，所以需要另行配置。  
比如：  
```xml
<bean>
   <mvc:resources mapping="/js/**" location="/js/"/>
</bean>
```
上述代码表示springMVC开放了部分的资源的访问权限。mapping代表访问时的地址，location代表具体的路径。
还有另外的代码：
```xml
<bean>
   <mvc:default-servlet-handler/>
</bean>
```  
表明如果SpringMVC在servlet中找不到对应的匹配地址时，就交给原始的容器，即tomcat来寻找对应的静态资源。
#### 数据乱码
* * *
在post请求时，数据会出现乱码问题，需要设置过滤器来过滤编码。  
```xml
<web-app>
   <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
</web-app>
```  
#### 参数绑定注解`@RequestParam`
* * *
当请求的参数名称与Controller的业务方法的参数不一致时，就需要使用注解`@RequestParam`来显示的绑定数据。当数据只有一个时，可以省略value属性值。
注解`@RequestParam`的几个参数：   

|参数|描述|
|---|---|
|value|请求的参数名称。|
|required|指定的请求参数是否必须包括，默认是true，提交时如果没有此参数则报错。|
|defaultValue|当没有指定请求参数时，则使用指定的默认值赋值。例：defaultValue="mosheyu"|

servlet使用：
```java
@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest17")
   @ResponseBody
   public void out17(@RequestParam(value = "name") String username) throws IOException {
      System.out.println(username.toString());
   }
}
```
#### 获取Restful风格的参数
* * *
一种软件架构、设计风格，不是标准，只是一个独特的方式，根据这种方式设计可以更简洁，更有层次感。  
Restful风格的请求是使用“url+请求方式”，表示一次请求的目的。HTTP协议中四个表示操作方式的动词如下：  
1. GET：用于获取资源。  
2. POST:用于新建资源。
3. PUT：用于更新资源。
4. DELETE:用于删除资源。

例如地址"/user/1"可以写成"/user/{id}，占位符{id}就是对应的1的值，在业务方法中使用注解`@PathVariable`进行占位符的匹配。  
HTTP访问：  
```
http://localhost:8080/acpt03/user/attest18/mosheyu
```
servlet使用：
```java

@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest18/{name}")
   @ResponseBody
   public void out18( @PathVariable(value = "name",required = true) String name) throws IOException {
      System.out.println(name.toString());
   }
}
```
#### 自定义类型转换器
* * *
SpringMVC 默认已经提供了一些常用的类型转换器，例如客户端提交的字符串转换成int型进行参数设置。
但是不是所有的数据类型都提供了转换器，没有提供的就需要自定义转换器，例如：日期类型的数据就需要自定义转换器。  
自定义类型转换器步骤：
1. 自定义类，实现接口Converter.
2. 在spring-mvc.xml配置文件中声明转换器。配置到转换服务工厂。
3. 在<annotation-driver>中引用转换器。

##### 自定义类
* * *
```java
package com.mosheyu.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String s) {
        //将日期的字符串转换成日期对象 进行返回
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}

```
##### 配置文件中声明转换器
* * *
在配置文件spring-mvc.xml中配置转换器。
```xml
<beans>
    <bean id="conversionService2" class="org.springframework.context.support.ConversionServiceFactoryBean">
        <property name="converters">
            <list>
                <bean class="com.mosheyu.converter.DateConverter"></bean>
            </list>
        </property>
    </bean>
</beans>
```
##### 引用转换器
* * *
```xml
<beans>
   <!--    MVC的注解驱动-->
   <mvc:annotation-driven conversion-service="conversionService2"/>
</beans>
```
#### 获得Servlet相关API
* * *
SpringMVC支持使用原始ServletAPI对象作为控制器方法的参数进行注入，常用的对象如下：
1. HttpServletRequest
2. HttpServletResponse
3. HttpSession

举例：
```java
@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest19")
   @ResponseBody
   public void out18(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
      System.out.println(request);
      System.out.println(response);
      System.out.println(session);
   }
}
```
#### 获得请求头
* * *
##### 注解`@RequestHeader`
* * *
使用注解`@RequestHeader`可以获得请求头信息，相当于web阶段的request.getHeader(name)。   

|属性|描述|
|---|---|
|value|请求头的名称。|
|required|是否必须携带此请求头。|
代码举例：
```java
@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest20")
   @ResponseBody
   public void out20(@RequestHeader(value = "User-Agent",required = false) String headerValue) throws IOException {
      System.out.println(headerValue);
   }
}
```
##### 注解`@CookieValue`
* * *
使用注解`@CookieValue`可以获得指定Cookie的值。

|属性|描述|
|---|---|
|value|指定cookie的名称。|
|required|是否必须携带此cookie。|
代码举例：
```java
@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest21")
   @ResponseBody
   public void out21(@CookieValue(value = "JSESSIONID",required = false) String jsessionid) throws IOException {
      System.out.println(jsessionid);
   }
}
```
#### 文件上传
* * *
文件上传客户端三要素
1. 文件上传表单项type=file。
2. 所在表单为post提交方式。
3. 表单的enctype属性是多部分表单形式，即enctype=“multipart/form-data”

代码举例：   
```
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/user/attest22" method="post" enctype="multipart/form-data">
        用户名：<input type="text" name="name"><br />
        文件：<input type="file" name="fileIn"><br />
        <input type="submit" value="提交">
    </form>
</body>
</html>
```
#### 文件上传原理
* * *
当form表单被修改为多部分表单时，`request.getParameter()`将失效。  
`enctype="application/x-www-form-urlencoded"`时，form表单的正文内容格式是**key=value&key=value&key=value**  
当form表单的`enctype`取值为`Mutipart/form-data`时，请求正文内容就会变成多部分形式。  
#### 单文件上传步骤
* * *
步骤：  
1. 导入fileupload和io坐标。
2. 配置文件上传解析器。
3. 编写文件上传代码。

##### 导坐标
* * *
pom.xml中导入坐标。
```xml
<dependencies>
   <dependency>
      <groupId>commons-fileupload</groupId>
      <artifactId>commons-fileupload</artifactId>
      <version>1.3.1</version>
   </dependency>
   <dependency>
      <groupId>commons-io</groupId>
      <artifactId>commons-io</artifactId>
      <version>2.3</version>
   </dependency>    
</dependencies>
```
##### 配置文件上传解析器
* * *
spring-mvc.xml中配置文件上传解析器。
```xml
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
        <property name="defaultEncoding" value="UTF-8"></property>
        <property name="maxUploadSize" value="500000"></property>
</bean>
```
##### 编写文件上传代码
* * *
前端页面代码：  
```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/user/attest22" method="post" enctype="multipart/form-data">
        用户名：<input type="text" name="name"><br />
        文件：<input type="file" name="uploadFile"><br />
        <input type="submit" value="提交">
    </form>
</body>
</html>
```
servlet代码：  
```java
@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest22")
   @ResponseBody
   public void out22(String name, MultipartFile uploadFile) throws IOException {
      System.out.println(name);
      String fileName = uploadFile.getOriginalFilename();
      uploadFile.transferTo(new File("D:\\www\\"+fileName));
   }
}
```
#### 多个文件上传
* * *
多文件上传与单文件上传差别不大，就是增加形参数量，还可以将形参作为形参数组。
前端页面代码：
```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/user/attest23" method="post" enctype="multipart/form-data">
        用户名：<input type="text" name="name"><br />
        文件：<input type="file" name="uploadFile"><br />
        文件：<input type="file" name="uploadFile2"><br />
        文件：<input type="file" name="uploadFile3"><br />
        <input type="submit" value="提交">
    </form>
</body>
</html>
```
servlet代码：
```java
@Controller
@RequestMapping("/user")
public class UserController {
   @RequestMapping("/attest23")
   @ResponseBody
   public void out23(String name, MultipartFile uploadFile,MultipartFile uploadFile2,MultipartFile uploadFile3) throws IOException {
      System.out.println(name);
      String fileName = uploadFile.getOriginalFilename();
      uploadFile.transferTo(new File("D:\\www\\"+fileName));
      String fileName2 = uploadFile2.getOriginalFilename();
      uploadFile.transferTo(new File("D:\\www\\"+fileName2));
      String fileName3 = uploadFile3.getOriginalFilename();
      uploadFile.transferTo(new File("D:\\www\\"+fileName3));
   }
}
```
# SSM学习第五章节——SpringJdbcTemplate基本使用
* * *
### JdbcTemplate概述
* * *
Spring框架中提供的一个对象，对原始的JdbcAPI对象进行了简单的封装，提供了部分操作的模板类。  
例：操作关系数据的`JdbcTemplate`和`HibernateTemplate`，操作nosql数据库的`RedisTemplate`，操作消息队列的`JmsTemplate`等等。  
### JdbcTemplate开发步骤
* * *
1. 导入spring-jdbc和spring-tx坐标。
2. 创建数据库表和实体。
3. 创建JdbcTemplate对象。
4. 执行数据库操作。

### 代码实现
* * *
1.导入坐标
```xml
<dependencies>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>5.0.5.RELEASE</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-tx</artifactId>
      <version>5.0.5.RELEASE</version>
    </dependency>
</dependencies>
```
2.创建数据库表和实体。
数据库建立表`account`，表中建立两个字段，name，varchar（50），money，double,0.
java建立实体类：  
```java
public class Account {
   private String name;
   private double money;
}
```
3.创建JdbcTemplate对象。
```java
@Test
public class JdbcTemplateTest {
   @Test
   public  void test1() throws PropertyVetoException {
      //创建数据源对象
      ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
      comboPooledDataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
      comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test?serverTimezone=UTC");
      comboPooledDataSource.setUser("root");
      comboPooledDataSource.setPassword("123456");

      JdbcTemplate jdbcTemplate = new JdbcTemplate();
      //设置数据源对象，知道数据库在哪儿。
      jdbcTemplate.setDataSource(comboPooledDataSource);
   }
}
```
4.执行数据库操作
```java
public class JdbcTemplateTest {
   @Test
   public  void test1() throws PropertyVetoException{
      //创建数据源对象
      ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
      comboPooledDataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
      comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test?serverTimezone=UTC");
      comboPooledDataSource.setUser("root");
      comboPooledDataSource.setPassword("123456");

      JdbcTemplate jdbcTemplate = new JdbcTemplate();
      //设置数据源对象，知道数据库在哪儿。
      jdbcTemplate.setDataSource(comboPooledDataSource);
      //执行操作
      int row = jdbcTemplate.update("insert into account values(?,?) ","tom",123);
      System.out.println(row);
   }
}
```
### Spring产生JdbcTemplate对象
* * *
配置applicationContext.xml文件。
```
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">
   <context:property-placeholder location="classpath:jdbc.properties"/>
   <bean id="datasSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
      <property name="driverClass" value="${jdbc.driver}"></property>
      <property name="jdbcUrl" value="${jdbc.url}"></property>
      <property name="user" value="${jdbc.username}"></property>
      <property name="password" value="${jdbc.password}"></property>
   </bean>
   <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
      <property name="dataSource" ref="datasSource"></property>
   </bean>
</beans>
```
配置jdbc.properties
```
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/test?serverTimezone=UTC
jdbc.username=root
jdbc.password=123456
```
实际应用：
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class jdbcCRUDTest {
    
   @Autowired
   private JdbcTemplate jdbcTemplate;
   
   @Test
   public void testUpdate(){
      int row = jdbcTemplate.update("update account set money=? where name=?",456,"tom");

   }
   @Test
   public void testDeleter(){
      int row = jdbcTemplate.update("delete from account where name=?","tom");
      System.out.println(row);
   }
   @Test
   public void testQuery(){
      List<Account> accounts = jdbcTemplate.query("select * from account", new BeanPropertyRowMapper<Account>(Account.class));
      Iterator<Account> it = accounts.iterator();
      while (it.hasNext()){
         System.out.println(it.next().toString());
      }
   }
   @Test
   public  void testQueryOne() {
      Account a = jdbcTemplate.queryForObject("select * from account where name = ?",
              new BeanPropertyRowMapper<Account>(Account.class), "tom");
      System.out.println(a.toString());
   }
   @Test
   public void testQueryCount(){
      Long aLong = jdbcTemplate.queryForObject("select count(*) from account", Long.class);
      System.out.println(aLong);
   }
}
```
# SSM学习第六章节——Spring项目练习
* * *
# SSM学习第七章节——SpringMVC拦截器
* * *
## 拦截器和过滤器的区别
* * *
|区别|过滤器|拦截器|
| --- | ---  | --- |
|拦截范围|是servlet规范中的一部分，任何javaWeb工程够可以使用|是SpringMVC框架自己的，只有使用了SpringMVC框架的工程才能用。|
|拦截范围|在Url-pattlem中配置了/*之后，可以对所有要访问的资源拦截。|只会拦截访问的控制方法，如果访问的是jsp，html，css，image或者js是不会进行拦截的。|
* * *
## 拦截器入门
* * *
### 步骤
* * *
1. 创建拦截器实现HandleInterceptor接口。
2. 配置拦截器。
3. 测试拦截器的拦截效果。
* * *
### 创建拦截器
* * *
```java
public class TargetInterceptor implements HandlerInterceptor {
    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle");
        return false;
    }
    //目标方法执行之后，视图返回之前返回
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("name","test");
        System.out.println("postHandle");
    }
    //整个流程都执行完毕后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
    }
}
```
### 配置拦截器
* * *
```xml
  <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/**"/>
            <bean class="com.mosheyu.interceptor.TargetInterceptor"/>
        </mvc:interceptor>
        <mvc:interceptor>
            <mvc:mapping path="/**"/>
            <bean class="com.mosheyu.interceptor.TargetInterceptor2"/>
        </mvc:interceptor>
    </mvc:interceptors>
```
### 拦截器方法说明
* * *
|方法|说明|
|---|---|
|preHandle()|在请求处理之前调用，返回值为布尔类型，如果为false，后续的拦截器和控制器都不会执行。|
|postHandle()|在请求处理之后，在DispathcherDervlet进行视图返回渲染之前调用。可以对Controller处理之后的ModelAndView对象进行操作。|
|afterCompletion()|整个请求结束之后执行。|
# SSM学习第八章节——SpringMVC异常处理机制
* * *
系统的Dao、service、Controller出现异常，都会通过throws Exception向上抛出。最后由SpringMVC`前端控制器`交由`异常处理器(HandlerExceptionResolver)`进行异常处理。  
异常处理的两种方式：  
1. 使用SpringMVC提供的简单异常处理器`SimpleMappingExceptionResolver`。（简单的异常映射处理器，对应异常跳转对应页面）
2. 实现Spring的异常处理接口`HandlerExceptionResolver`自定义异常处理器。  
## 简单异常处理器
SpringMVC已经定义好了该类型转换器，在使用时根据需求，进行相应的异常与视图的映射配置。
```xml
<!--    配置异常处理器-->
    <bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
<!--        通用的错误提示页面-->
        <property name="defaultErrorView" value="error"></property>
        <property name="exceptionMappings">
            <map>
                <entry key="java.lang.ClassCastException" value="error1"></entry>
                <entry key="com.mosheyu.exception.ExceptionTest1" value="error2"></entry>
            </map>
        </property>

    </bean>
```
## 自定义异常处理器
步骤：  
1. 创建异常处理器，实现HandlerExceptionResolver。
2. 配置异常处理器。
3. 编写异常页面。  
### 创建异常处理器
```java
public class ExceptionTest2 implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView();

        if(e instanceof ExceptionTest1){
            modelAndView.addObject("news","1自定义异常。");
        }else if (e instanceof ClassCastException){
            modelAndView.addObject("news","2类型转换异常");
        }
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
```
### 配置异常处理器
```xml
 <bean class="com.mosheyu.exception.ExceptionTest2"></bean>
```
# SSM学习第九章节——SpringAOP


