package com.mosheyu.dao.impl;

import com.mosheyu.dao.UserDao;

public class UserDaoImpl implements UserDao {
    @Override
    public void out() {
        System.out.println("测试web环境");
    }
}
