package com.mosheyu.test;

import com.mosheyu.domain.Orders;
import com.mosheyu.domain.User;
import com.mosheyu.mapper.OrderMapper;
import com.mosheyu.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class OrdersMapperTest2 {
    private SqlSession sqlSession;
    public UserMapper Mapper;

    @Before
    public void before() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        sqlSession = build.openSession();
        Mapper = sqlSession.getMapper(UserMapper.class);


    }


    @Test
    public void test1() throws IOException {
        List<User> userList = Mapper.findUserAndOrdersAll();
        for(User user:userList){
            System.out.println(user);
        }
    }


    @Test
    public void test2() throws IOException {
        List<User> userList = Mapper.findUserAndRoleAll();
        for(User user:userList){
            System.out.println(user);
        }
    }


    @After
    public void after(){
        sqlSession.commit();
        sqlSession.close();
    }
}
