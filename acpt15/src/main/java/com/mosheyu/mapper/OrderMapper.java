package com.mosheyu.mapper;

import com.mosheyu.domain.Orders;
import com.mosheyu.domain.User;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderMapper {

//    @Select("select *,o.id oid from orders o,user u where o.uid=u.id")
//    @Results({
//            @Result(column = "id",property = "id"),
//            @Result(column = "ordertime",property = "ordertime"),
//            @Result(column = "total",property = "total"),
//            @Result(column = "uid",property = "user.id"),
//            @Result(column = "username",property = "user.username"),
//            @Result(column = "password",property = "user.password"),
//    })
//    public List<Orders> findAll();
//


    @Select("select * from orders")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "ordertime",column = "ordertime"),
            @Result(property = "total",column = "total"),
            @Result(property = "user",  //要封装的属性名称
                    column = "id",      //根据哪个字段去查询user表的数据
                    javaType = User.class,  //要封装的实体类型
                    //select属性代表查询哪个接口的方法，去获得要获得的数据
                    one = @One(select = "com.mosheyu.mapper.UserMapper.findById"))
    })
    public List<Orders> findAll();


    @Select("select * from orders where uid=#{uid}")
    public List<Orders> findByUid(int id);
}
