package com.mosheyu.service.impl;

import com.mosheyu.dao.UserDao;
import com.mosheyu.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void out() {
        userDao.out();
    }
}
