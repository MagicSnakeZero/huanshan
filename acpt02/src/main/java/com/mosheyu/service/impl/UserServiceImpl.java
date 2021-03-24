package com.mosheyu.service.impl;

import com.mosheyu.dao.UserDao;
import com.mosheyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;


//<bean id="userService" class="com.mosheyu.service.impl.UserServiceImpl">
//<property name="userDao" ref="userDao"></property>
//</bean>

//@Component("userService")
@Service("userService")
public class UserServiceImpl implements UserService {

//    @Autowired              //自动注入，根据数据类型从Spring容器中进行匹配。可以单独使用
//    @Qualifier("userDao")   //输入要注入的bean的id。根据id的名称从容器中进行匹配，使用时需要已有@Autowired注解。
    @Resource(name="userDao")
    private UserDao userDao;
//    @Resource(name="userDao")可能找不到包，需要添加maven配置。
//        <dependency>
//            <groupId>javax.annotation</groupId>
//            <artifactId>javax.annotation-api</artifactId>
//            <version>1.3</version>
//        </dependency>


//    public void setUserDao(UserDao userDao) {
//        this.userDao = userDao;
//    }





    //    @Value("mosheyu")       //注入普通数据
    @Value("${jdbc.driver}")        //可以调用spring容器中命名空间中的值
    private String driver;


    @PostConstruct          //标注这个方法是Bean的初始化方法
    public void init(){
        System.out.println("初始化方法。");
    }

    @Override
    public void out() {
        userDao.out();
        System.out.println(driver);
    }

    @PreDestroy              //标注这个方法是Bean的销毁方法
    public void destory(){
        System.out.println("销毁方法。");
    }
}
