package com.mosheyu;

import com.mosheyu.dao.UserDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class demoBean {


    @Test
    public void test1(){

        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao = (UserDao)app.getBean("userDao");
        userDao.out();
    }
}
