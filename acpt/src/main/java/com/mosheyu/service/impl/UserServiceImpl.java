package com.mosheyu.service.impl;

import com.mosheyu.dao.UserDao;
import com.mosheyu.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceImpl implements UserService {




    @Override
    public void save() {
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao = (UserDao)app.getBean("userDao");
        userDao.out();
    }
}
