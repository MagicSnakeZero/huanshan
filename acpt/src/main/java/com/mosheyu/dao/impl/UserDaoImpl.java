package com.mosheyu.dao.impl;

import com.mosheyu.dao.UserDao;

public class UserDaoImpl implements UserDao {

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


}
