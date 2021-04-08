package com.mosheyu.anno;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component("aspectTestAnno")
@Aspect             //标准当前类是一个切面类。
public class AspectTest {

    //配置前置增强
    @Before(value = "execution(public void com.mosheyu.anno.Target.save())")
    public void before(){
        System.out.println("前置增强。");
    }

    /* 环绕增强  */
    @Around(value = "execution(public void com.mosheyu.anno.Target.save())")
    public Object aroud(ProceedingJoinPoint pro) throws Throwable {
        System.out.println("前环绕。");
        Object ob = pro.proceed();
        System.out.println("后环绕。");
        return ob;
    }

}
