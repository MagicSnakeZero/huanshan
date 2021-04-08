package com.mosheyu.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String arg[]){

        Target target = new Target();

        Advice advice = new Advice();

        //返回值为动态生成之后的代理对象。可以用接口接收返回值。
        TargetInterface targetInterface =(TargetInterface)  Proxy.newProxyInstance(
                target.getClass().getClassLoader(), //目标对象类加载器
                target.getClass().getInterfaces(),//目标对象相同的接口字节码对象数组
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //调用代理对象的方法，实际执行的是invoke方法。

                        //前置增强
                        advice.before();
                        //执行目标方法。
                        Object re = method.invoke(target, args);
                        //后置增强
                        advice.after();

                        return re;
                    }
                }
        );
        targetInterface.save();



    }
}
