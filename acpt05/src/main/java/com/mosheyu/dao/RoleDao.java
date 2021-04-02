package com.mosheyu.dao;

import com.mosheyu.domain.Role;

import java.util.List;

public interface RoleDao{
    public List<Role> list();

    void save(Role role);
}
