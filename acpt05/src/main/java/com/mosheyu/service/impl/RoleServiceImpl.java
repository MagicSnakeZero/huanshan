package com.mosheyu.service.impl;

import com.mosheyu.dao.RoleDao;
import com.mosheyu.domain.Role;
import com.mosheyu.service.RoleService;

import java.util.List;

public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    public void setReloDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> list() {
        List<Role> list = roleDao.list();
        return list;
    }

    @Override
    public void save(Role role) {
        roleDao.save(role);
    }
}
