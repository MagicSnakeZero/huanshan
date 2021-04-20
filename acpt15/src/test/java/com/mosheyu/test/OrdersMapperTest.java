package com.mosheyu.test;

import com.mosheyu.domain.Orders;
import com.mosheyu.domain.User;
import com.mosheyu.mapper.OrderMapper;
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

public class OrdersMapperTest {
    private SqlSession sqlSession;
    public OrderMapper orderMapper;

    @Before
    public void before() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        sqlSession = build.openSession();
        orderMapper = sqlSession.getMapper(OrderMapper.class);


    }


    @Test
    public void test1() throws IOException {
        List<Orders> orders = orderMapper.findAll();
        for(Orders order:orders){
            System.out.println(order);
        }
    }




    @After
    public void after(){
        sqlSession.commit();
        sqlSession.close();
    }
}
