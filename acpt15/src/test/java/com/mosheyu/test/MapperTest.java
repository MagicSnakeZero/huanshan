package com.mosheyu.test;

import com.mosheyu.domain.User;
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
import java.util.Date;
import java.util.List;

public class MapperTest {

    private SqlSession sqlSession;
    private UserMapper userMapper;

    @Before
    public void before() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        sqlSession = build.openSession();
        userMapper = sqlSession.getMapper(UserMapper.class);


    }


    @Test
    public void test1() throws IOException {
        List<User> userList = userMapper.findAll();
        for (User user:userList){
            System.out.println(user);
        }
    }

    @Test
    public void save(){
        User user = new User();
        user.setId(6);
        user.setUsername("save22");
        user.setPassword("12344444");
        user.setBirthday(new Date());
        userMapper.save(user);
    }



    @After
    public void after(){
        sqlSession.commit();
        sqlSession.close();
    }

}
