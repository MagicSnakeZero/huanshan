package com.mosheyu.service.impl;

import com.mosheyu.dao.RoleDao;
import com.mosheyu.dao.UserDao;
import com.mosheyu.domain.User;
import com.mosheyu.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> list() {
        List<User> list = userDao.list();
        return list;
    }

    @Override
    public void save(User user, Long[] roleIds) {
        Long userId = userDao.save(user);
        userDao.saveUserRole(userId,roleIds);
    }

    @Override
    public void del(Long userId) {
        userDao.del(userId);
    }

    @Override
    public User login(String username, String password) {
        User user = userDao.login(username,password);
        return user;
    }
}
