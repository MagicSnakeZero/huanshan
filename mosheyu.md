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
~~~
<context:property-placeholder location="classpath:jdbc.properties"/>
<bean id="datasSourse" class="com.mchange.v2.c3p0.ComboPooledDataSource">
      <property name="driverClass" value="${jdbc.driver}"></property>
      <property name="jdbcUrl" value="${jdbc.url}"></property>
      <property name="user" value="${jdbc.username}"></property>
      <property name="password" value="${jdbc.password}"></property>
</bean>
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




























