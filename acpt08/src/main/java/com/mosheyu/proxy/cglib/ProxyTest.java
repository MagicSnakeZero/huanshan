package com.mosheyu.proxy.cglib;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String arg[]){

        Target target = new Target();

        Advice advice = new Advice();

        //返回值为动态生成之后的代理对象。基于cglib
//        1.创建增强器。
        Enhancer enhancer = new Enhancer();
//        2.设置父类（目标）
        enhancer.setSuperclass(Target.class);
//        3.设置回调
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                //执行前置
                advice.before();
                //执行目标
                Object invoke = method.invoke(target, objects);
                //执行后置
                advice.after();
                return invoke;
            }
        });
//        4.创建代理对象
        Target target1 = (Target) enhancer.create();
        System.out.println("****");
        target.save();
        System.out.println("****");
        target1.save();


    }
}
