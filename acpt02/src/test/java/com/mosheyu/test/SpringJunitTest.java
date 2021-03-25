package com.mosheyu.test;


import com.mosheyu.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

//配置使用spring提供的内核去跑____pringJUnit4ClassRunner 需要搭配 4.12 及以上版本的 Junit 才可以执行,不然会报错
@RunWith(SpringJUnit4ClassRunner.class)
//指定配置文件位置
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringJunitTest {

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource datasSource;




    @Test
    public void test1(){
        userService.out();
    }


    @Test
    public void test2() throws SQLException {
        System.out.println(datasSource.getConnection());
    }

}
