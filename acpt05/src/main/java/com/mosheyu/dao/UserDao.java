package com.mosheyu.dao;

import com.mosheyu.domain.User;

import java.util.List;

public interface UserDao {
    public List<User> list();

    Long save(User user);

    void saveUserRole(Long id, Long[] roleIds);


    void del(Long userId);

    User login(String username, String password);
}
