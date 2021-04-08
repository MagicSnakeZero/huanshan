package com.mosheyu.aop;

import org.aspectj.lang.ProceedingJoinPoint;

public class AspectTest {
    public void before(){
        System.out.println("前置增强。");
    }

    /* 环绕增强  */
    public Object aroud(ProceedingJoinPoint pro) throws Throwable {
        System.out.println("前环绕。");
        Object ob = pro.proceed();
        System.out.println("后环绕。");
        return ob;
    }

}
