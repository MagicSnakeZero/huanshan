package com.mosheyu.dao.impl;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mosheyu.dao.RoleDao;
import com.mosheyu.domain.Role;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Iterator;
import java.util.List;

public class RoleDaoImpl implements RoleDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Role> list() {
        List<Role> list = jdbcTemplate.query("select * from sys_role",new BeanPropertyRowMapper<Role>(Role.class));
        return list;
    }

    @Override
    public void save(Role role) {
        jdbcTemplate.update("insert into sys_role value (?,?,?)",null,role.getRoleName(),role.getRoleDesc());
    }
}
