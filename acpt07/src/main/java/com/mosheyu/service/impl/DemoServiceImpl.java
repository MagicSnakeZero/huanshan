package com.mosheyu.service.impl;

import com.mosheyu.exception.ExceptionTest1;
import com.mosheyu.service.DemoService;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public void show1() {
        System.out.println("类型转换异常。");
        Object str = "mosheyu";
        Integer num = (Integer) str;
    }

    @Override
    public void show2() {
        System.out.println("除零异常。");
        int i = 1/0;
    }

    @Override
    public void show3() throws FileNotFoundException {
        System.out.println("文件找不到异常。");
        InputStream in = new FileInputStream("C:/xxx/xxx/xxx.txt");
    }

    @Override
    public void show4() {
        System.out.println("空指针异常。");
        String str = null;
        str.length();
    }

    @Override
    public void show5() throws ExceptionTest1 {
        System.out.println("自定义异常。");
        throw new ExceptionTest1();
    }
}
