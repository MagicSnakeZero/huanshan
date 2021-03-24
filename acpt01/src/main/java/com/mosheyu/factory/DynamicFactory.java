package com.mosheyu.factory;

import com.mosheyu.dao.UserDao;
import com.mosheyu.dao.impl.UserDaoImpl;

public class DynamicFactory {

    /**
     *  工厂实例方法实例化
     * @return
     */
    public UserDao getUserDao(){
        return new UserDaoImpl();
    }


}
