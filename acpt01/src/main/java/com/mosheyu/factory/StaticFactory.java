package com.mosheyu.factory;

import com.mosheyu.dao.UserDao;
import com.mosheyu.dao.impl.UserDaoImpl;

public class StaticFactory {


    /**
     *  工厂静态方法实例化
     * @return
     */
    public static UserDao getUserDao(){
        return new UserDaoImpl();
    }
}
