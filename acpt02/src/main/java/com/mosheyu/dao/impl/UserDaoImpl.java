package com.mosheyu.dao.impl;

import com.mosheyu.dao.UserDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


//<bean id="userDao" class="com.mosheyu.dao.impl.UserDaoImpl"></bean>
//@Component("userDao")
@Repository("userDao")
public class UserDaoImpl implements UserDao {


    @Override
    public void out() {
        System.out.println("执行。。。。");
    }
}
