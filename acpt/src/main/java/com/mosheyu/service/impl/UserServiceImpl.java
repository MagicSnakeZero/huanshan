package com.mosheyu.service.impl;

import com.mosheyu.dao.UserDao;
import com.mosheyu.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceImpl implements UserService {

    //设置dao属性以及setter方法。以供依赖注入。
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void save() {
        userDao.out();
    }
}
