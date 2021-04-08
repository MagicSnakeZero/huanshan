package com.mosheyu.anno;

import org.springframework.stereotype.Component;

@Component("targetanno")
public class Target implements TargetInterface {

    @Override
    public void save() {
        System.out.println("save方法。");
    }
}
