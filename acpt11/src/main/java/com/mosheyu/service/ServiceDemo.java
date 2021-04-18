package com.mosheyu.service;

import com.mosheyu.dao.UserMapper;
//import com.mosheyu.dao.impl.UserMapperImpl;
import com.mosheyu.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ServiceDemo {

    public static void main(String[] args) throws IOException {
//        传统方式
//        UserMapper userMapper = new UserMapperImpl();
//        List<User> users = userMapper.findAll();
//        System.out.println(users);
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = build.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> user = mapper.findAll();
        System.out.println(user);
        System.out.println("*****");
        User user1 = mapper.findById(1);
        System.out.println(user1);


    }
}
