package com.mosheyu.service;

import com.mosheyu.domain.Role;

import java.util.List;

public interface RoleService {
    public List<Role> list();

    void save(Role role);
}
