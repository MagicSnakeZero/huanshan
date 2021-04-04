package com.mosheyu.service;

import com.mosheyu.domain.User;

import java.util.List;

public interface UserService {
    public List<User> list();

    void save(User user, Long[] roleIds);


    void del(Long userId);

    User login(String username, String password);
}
