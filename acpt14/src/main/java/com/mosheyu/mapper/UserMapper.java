package com.mosheyu.mapper;

import com.mosheyu.domain.User;

import java.util.List;

public interface UserMapper {
    public List<User> findAll();
    public List<User> findUserAndRoleAll();
}
