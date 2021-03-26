# 墨折羽
## SSM学习第一章节——Spring入门
学习SSM的第一章，主要是Spring的入门，搭建项目，导包（使用了maven），配置文件。
Bean的取值范围：
1. singleton：默认值，单例的。spring容器启动时就构建唯一的Bean。
2. prototype：多例的。使用时构建。
3. ~~

Bean生命周期配置
1. init-method:指定类的初始化方法
2. destroy-method:指定类的销毁方法

Bean的三种实例化方式：
1. 无参构造方法实例化
2. 工厂静态方法实例化
3. 工厂实例方法实例化

Bean依赖注入：把某个类所需要的形参，属性传到这个类中。
注入方式：
1. setter注入
    1. 使用bean的子标签<property>根据name确定setter方法，然后选择ref注入引用，或者使用value注入值
    2. 使用命名空间，调用同处于spring容器中的bean。
    
2. 构造方法注入，给bean构造方法传递值。

## SSM学习第二章节——Spring配置数据源
#### spring配置数据源
数据源，即数据库连接池。
常见的数据源：DBCP、C3P0、BoneCP、Druid等。
开发步骤：
1. 导包，导入数据源的坐标和数据库驱动坐标。
2. 创建，创建数据源对象。
3. 设置，设置数据源的基本连接数据，账号密码等。
4. 使用及归还，使用数据源获取连接资源和归还连接资源。

###### 加载配置文件
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
#### spring注解开发
###### 原始注解
|注解| 说明 |
| --- | ---|
|@Component|在类上使用，实例化Bean。|
|@Controller|使用在web层上实例化Bean。|
|@Service|使用在service层上实例化Bean。|
|@Repository|使用在dao层上实例化Bean。|
|Autowired|在普通数据类型上使用，根据类型进行依赖注入。|
|Qualifier|与@Autowired一起使用，根据名称进行依赖注入。|
|@Resource|相当于@Autowired+@Qualifier，根据名称进行注入。|
|@Value|注入普通属性，可以指定值，以及spring容器，命名空间的值。|
|@Scope|标注Bean的作用范围，比如单例和多例。|
|@PostConstruce|在方法上使用，标注Bean的初始化方法。|
|@PreDestroy|在方法上使用，标注Bean的销毁方法。|
###### 新注解
|注解| 说明 |
| --- | ---|
|@Configuration|指定当前类是一个Spring配置类，创建容器时会在该类上加载注解。|
|@ComponentScan|指定Spring在初始化容器要扫描的包。|
|@Bean|使用在方法上，将方法的返回值存储到spring容器中。|
|PropertySOurce|加载.properties文件中的配置。|
|@import|导入其他配置类。|
#### spring集成Junit
步骤：
1. 导入spring集成Junit的坐标。
2. 使用@Runwith注解替换原来的运行期
3. 使用@ContextConfiguration指定配置文件或配置类。
4. 使用@Autowired注入需要测试的对象。
5. 使用@Test创建测试方法进行测试

注意：SpringJUnit4ClassRunner 需要搭配 4.12 及以上版本的 Junit 才可以执行。
## SSM学习第二章节——SpringMVC
#### 配置Spring的Web环境
配置servlet，继承HttpServlet类，或者实现接口。
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
此时获取获取应用上下文还需要记住ApplicationContext在ServletContext域中的别名，所以单独的写了一个静态工具方法，返回应用上下文对象。在使用时，调用相应的方法即可获取。
```
    public static ApplicationContext getApplicationContext(ServletContext servletContext){
        return (ApplicationContext)servletContext.getAttribute("app");
    }
```
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
#### SpringMVC配置
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
#### SpringMVC的组件解析
###### SpringMVC的相关组件
|组件|名称|
|---|---|
|前端控制器|DispathcherServlet|
|处理器映射器|HandlerMapping|
|处理器适配器|HandlerAdapter|
|处理器|Handler|
|视图解析器|View Resolver|
|视图|view|
###### SpringMVC执行流程：
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

###### SpringMVC注解解析：
**@RequestMapping**:请求映射，建立请求url和处理请求方法之间建立对应关系。
属性：

|属性|作用|
|---|---|
|value|指定请求的URL.|
|mathod|指定请求的方式。指get，post等方法，使用现成的枚举类型。例：<br />`method=RequestMethod.GET`|
|params|指定限制请求参数的条件，支持简单的表达式，要求请求参数的key和value必须和配置的一致。例：<br />1. `params={"name"}`指定参数必须有name参数。<br />2. `params={"moeny!100"}`指定请求参数中moeny不能是100.|
## SSM学习第三章节——SpringMVC具体应用






















