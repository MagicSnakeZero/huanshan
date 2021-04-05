package com.mosheyu.service;

import com.mosheyu.exception.ExceptionTest1;

import java.io.FileNotFoundException;

public interface DemoService {
    public void show1();
    public void show2();
    public void show3() throws FileNotFoundException;
    public void show4();
    public void show5() throws ExceptionTest1;
}
