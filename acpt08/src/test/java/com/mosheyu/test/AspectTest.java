package com.mosheyu.test;


import com.mosheyu.anno.TargetInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)             //配置测试引擎
@ContextConfiguration("classpath:applicationContext.xml")       //配置配置文件
public class AspectTest {

    @Autowired
    private TargetInterface targetInterface;

    @Test
    public void test1(){
        targetInterface.save();
    }


}
