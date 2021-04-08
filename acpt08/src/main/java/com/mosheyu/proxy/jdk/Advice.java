package com.mosheyu.proxy.jdk;

public class Advice {
    public void before(){
        System.out.println("目标之前增强。");
    }
    public void after(){
        System.out.println("目标之后增强。");
    }
}
