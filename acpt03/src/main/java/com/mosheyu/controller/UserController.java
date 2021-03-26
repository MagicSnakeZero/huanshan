package com.mosheyu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/attest")
    public String out(){
        System.out.println("控制器执行了。");
        return "/testSpringMVC.jsp";
//        return "testSpringMVC";             //解析   /jsp/  +testSpringMVC + .jsp
    }
}
