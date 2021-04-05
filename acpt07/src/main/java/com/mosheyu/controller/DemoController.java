package com.mosheyu.controller;

import com.mosheyu.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping(value = "/show")
    public String show(@RequestParam(value = "name",required = true)String name)throws Exception{
        System.out.println("show方法执行。");
        switch (Integer.parseInt(name)){
            case 1:demoService.show1();break;
            case 2:demoService.show2();break;
            case 3:demoService.show3();break;
            case 4:demoService.show4();break;
            case 5:demoService.show5();break;
        }


        return "index";
    }
}
