package com.mosheyu.test;

import com.mosheyu.domain.Order;
import com.mosheyu.domain.User;
import com.mosheyu.mapper.OrderMapper;
import com.mosheyu.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class MapperTest {
    @Test
    public void test1() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = build.openSession();

        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

        List<Order> orders = orderMapper.findAll();

        for (Order order:orders){
            System.out.println(order);
        }

        sqlSession.commit();
        sqlSession.close();

    }
    @Test
    public void test2() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = build.openSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        List<User> userList = userMapper.findAll();

        for (User user:userList){
            System.out.println(user);
        }

        sqlSession.commit();
        sqlSession.close();

    }
    @Test
    public void test3() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = build.openSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        List<User> userList = userMapper.findUserAndRoleAll();

        for (User user:userList){
            System.out.println(user);
        }

        sqlSession.commit();
        sqlSession.close();

    }
}
