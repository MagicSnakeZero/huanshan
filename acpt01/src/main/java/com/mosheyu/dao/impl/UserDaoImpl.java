package com.mosheyu.dao.impl;

import com.mosheyu.dao.UserDao;
import com.mosheyu.domain.User;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class UserDaoImpl implements UserDao {

    private List<String> strList;
    private Map<String, User> userMap;
    private Properties properties;

    private String username;
    private int age;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void out() {


        System.out.println(strList);
        System.out.println(userMap);
        System.out.println(properties);
        System.out.println(username+":____"+age);
    }

    public void setStrList(List<String> strList) {
        this.strList = strList;
    }

    public void setUserMap(Map<String, User> userMap) {
        this.userMap = userMap;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }





    /*
    public UserDaoImpl() {
        System.out.println("无参方法创建");
    }



    public void init(){
        System.out.println("初始化方法");
    }

    @Override
    public void out() {
        System.out.println("测试是否运行");
    }

    public void destory(){
        System.out.println("销毁的方法。");
    }
*/


}
