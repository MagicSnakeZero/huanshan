package com.mosheyu.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mosheyu.config.SpringCofiguration;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class DataSourceTest {


    //测试手动创建c3p0的数据源。
    @Test
    public void test1() throws PropertyVetoException, SQLException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();         //创建对象
        dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");                  //设置驱动
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");              //设置地址
        dataSource.setUser("root");                                             //设置用户名
        dataSource.setPassword("123456");                                       //设置密码
        Connection connection = dataSource.getConnection();                     //获取资源
        System.out.println(connection);                                         //测试连接状态，不为空即可

        connection.close();                                                     //关闭资源（实际是归还资源到c3p0中）
    }



    //测试手动创建druid的数据源。
    @Test
    public void test2() throws PropertyVetoException, SQLException {
        DruidDataSource dataSource = new DruidDataSource();                     //创建对象
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");              //设置驱动
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");                  //设置地址
        dataSource.setUsername("root");                                         //设置用户名
        dataSource.setPassword("123456");                                       //设置密码
        Connection connection = dataSource.getConnection();                     //获取资源
        System.out.println(connection);                                         //测试连接状态，不为空即可

        connection.close();                                                     //关闭资源（实际是归还资源到druid中）
    }



    //测试手动创建c3p0的数据源-———plus,加载配置文件。
    @Test
    public void test3() throws PropertyVetoException, SQLException {
        //1.读取配置文件,方法有很多种，此处是其中之一
        ResourceBundle resourceBundle =ResourceBundle.getBundle("jdbc");
        //getBundle加载的路径相当于类路径下，即resources，而且不需要扩展名，默认读取properties.
        String drive = resourceBundle.getString("jdbc.driver");
        String url = resourceBundle.getString("jdbc.url");
        String username = resourceBundle.getString("jdbc.username");
        String password = resourceBundle.getString("jdbc.password");


        DruidDataSource dataSource = new DruidDataSource();                     //创建对象
        dataSource.setDriverClassName(drive);                                   //设置驱动
        dataSource.setUrl(url);                                                 //设置地址
        dataSource.setUsername(username);                                       //设置用户名
        dataSource.setPassword(password);                                       //设置密码
        Connection connection = dataSource.getConnection();                     //获取资源
        System.out.println(connection);                                         //测试连接状态，不为空即可

        connection.close();                                                     //关闭资源（实际是归还资源到druid中）
    }

    //测试spring容器产生数据源对象
    @Test
    public void test4() throws PropertyVetoException, SQLException {
       ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        ComboPooledDataSource comboPooledDataSource = app.getBean(ComboPooledDataSource.class);
        System.out.println(comboPooledDataSource);
        comboPooledDataSource.close();


    }

    @Test
    public void test5() throws PropertyVetoException, SQLException {
        ApplicationContext app = new AnnotationConfigApplicationContext(SpringCofiguration.class);
        ComboPooledDataSource comboPooledDataSource = app.getBean(ComboPooledDataSource.class);
        System.out.println(comboPooledDataSource);
        comboPooledDataSource.close();


    }


}
