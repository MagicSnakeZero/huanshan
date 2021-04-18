package com.mosheyu.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mosheyu.domain.User;
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

public class MyBaitsTest {

    @Test
    public void test1() throws IOException {
//        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
//        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
//        SqlSession sqlSession = build.openSession();
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = build.openSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        user.setId(5);
        user.setUsername("test");
        user.setPassword("12334");
        user.setBirthday(new Date());

        userMapper.save(user);

        sqlSession.commit();
        sqlSession.close();

    }
    @Test
    public void test2() throws IOException {
//        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
//        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
//        SqlSession sqlSession = build.openSession();
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = build.openSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user = userMapper.findById(5);

        System.out.println(user);

        sqlSession.commit();
        sqlSession.close();

    }
    @Test
    public void test3() throws IOException {
//        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
//        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
//        SqlSession sqlSession = build.openSession();
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = build.openSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

//        设置分页的相关参数         当前页 、 每页显示的条数
        PageHelper.startPage(2,2);

        List<User> users = userMapper.findAll();

        for (User user : users){
            System.out.println(user);
        }
//        获取分页相关的参数
        PageInfo<User> pageInfo = new PageInfo<>(users);
        System.out.println("当前页:"+pageInfo.getPageNum());
        System.out.println("每页显示条数:"+pageInfo.getPageSize());
        System.out.println("总条数:"+pageInfo.getTotal());
        System.out.println("总页数:"+pageInfo.getPages());
        System.out.println("上一页页数:"+pageInfo.getPrePage());
        System.out.println("上一页页数:"+pageInfo.getNextPage());
        System.out.println("是否是第一页:"+pageInfo.isIsFirstPage());
        System.out.println("是否是最后一页:"+pageInfo.isIsLastPage());


        sqlSession.commit();
        sqlSession.close();

    }
}
