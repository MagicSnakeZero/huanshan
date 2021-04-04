package com.mosheyu.dao.impl;

import com.mosheyu.dao.UserDao;
import com.mosheyu.domain.Role;
import com.mosheyu.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> list() {
        List<User> list = jdbcTemplate.query("select * from sys_user",new BeanPropertyRowMapper<User>(User.class));
        for (User user: list){
            List<Role> roleList = jdbcTemplate.query("select * from sys_role ro,sys_user_role ur where ro.id = ur.roleId and ur.userId = ?",new BeanPropertyRowMapper<Role>(Role.class),user.getId());
            user.setRoles(roleList);
        }
        return list;
    }

    @Override
    public Long save(final User user) {
//        jdbcTemplate.update("insert  into sys_user values (?,?,?,?,?)",null,user.getUsername(),user.getEmail(),user.getPassword(),user.getPhoneNum());
        //1.创建PreparedStatementCreator
        PreparedStatementCreator creator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement("insert  into sys_user values (?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setObject(1,null);
                preparedStatement.setString(2,user.getUsername());
                preparedStatement.setString(3,user.getEmail());
                preparedStatement.setString(4,user.getPassword());
                preparedStatement.setString(5,user.getPhoneNum());
                return preparedStatement;
            }
        };
//        2.创建keyHolder
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(creator,keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void saveUserRole(Long id, Long[] roleIds) {
        for (Long roleId:roleIds){
            jdbcTemplate.update("insert into sys_user_role values(?,?)",id,roleId);
        }
    }

    @Override
    public void del(Long userId) {
        jdbcTemplate.update("delete from sys_user_role where userId =? ",userId);
        jdbcTemplate.update("delete from sys_user where Id =? ",userId);
    }

    @Override
    public User login(String username, String password) {
        System.out.println(username.toString());
        System.out.println(password.toString());
        User user = jdbcTemplate.queryForObject("select * from sys_user where username = ? and password = ?",new BeanPropertyRowMapper<User>(User.class),username,password);
        return user;
    }

}
