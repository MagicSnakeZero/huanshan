# 墨折羽
## acpt01 ——SSM学习第一章节
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

## acpt02 ——SSM学习第二章节

