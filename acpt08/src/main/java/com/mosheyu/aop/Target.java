package com.mosheyu.aop;

public class Target implements TargetInterface {

    @Override
    public void save() {
        System.out.println("save方法。");
    }
}
