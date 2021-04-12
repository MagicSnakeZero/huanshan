package com.mosheyu.test;

import com.mosheyu.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyBatisTest {

    @Test
    public void test1() throws IOException {
//        //加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
//        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
//        //获得sqlSession工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
//         SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
//        // 获得sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
//         SqlSession sqlSession = sqlSessionFactory.openSession();
//        // 执行sql语句
        List<User> userList = sqlSession.selectList("userMapper.findAll");
//         List<User> userList = sqlSession.selectList("userMapper.findAll");
//        // 打印结果
         System.out.println(userList);
//         System.out.println(userList);
//        // 释放资源
         sqlSession.close();
//         sqlSession.close();

    }


    @Test
    public void test2() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(5);
        user.setUsername("testinsert");
        user.setPassword("123456");
        int i = sqlSession.insert("userMapper.save", user);
        //myBatis默认有事务，不提交，需要手动提交。
        sqlSession.commit();
        System.out.println(i);
        sqlSession.close();

    }
    @Test
    public void test3() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(5);
        user.setUsername("testupdate");
        user.setPassword("1654");
        int i = sqlSession.update("userMapper.update", user);
        sqlSession.commit();
        System.out.println(i);
        sqlSession.close();
    }
    @Test
    public void test4() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Integer integer = 5;
        int i = sqlSession.delete("userMapper.delete", 5);
        sqlSession.commit();
        System.out.println(i);
        sqlSession.close();
    }
}
