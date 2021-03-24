package com.mosheyu.web;

import com.mosheyu.service.UserService;
import com.mosheyu.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserController {


    public static void main(String[] args) {
        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = app.getBean(UserServiceImpl.class);
        userService.out();
    }
}
