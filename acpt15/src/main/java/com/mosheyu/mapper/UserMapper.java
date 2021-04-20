package com.mosheyu.mapper;

import com.mosheyu.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    @Insert(value = "insert into user values(#{id},#{username},#{password},#{birthday})")
    public void save(User user);
    @Update("update user set username=#{username},password=#{password} where id=#{id}")
    public void update(User user);
    @Delete("delete from user where id=#{id}")
    public void delete(int id);
    @Select("select * from user where id=#{id}")
    public void findById(int id);
    @Select("select * from user")
    public List<User> findAll();
    @Select("select * from user")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "password",column = "password"),
            @Result(property = "birthday",column = "birthday"),
            @Result(property = "ordersList",
                    column = "id",
                    javaType = List.class,
                    many = @Many(select = "com.mosheyu.mapper.OrderMapper.findByUid"))
    })
    public List<User> findUserAndOrdersAll();

    @Select("select * from user")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "roleList", column = "id",
                    javaType = List.class,
                    many = @Many(select = "com.mosheyu.mapper.RoleMapper.findByUid"))
    })
    public List<User> findUserAndRoleAll();
}
