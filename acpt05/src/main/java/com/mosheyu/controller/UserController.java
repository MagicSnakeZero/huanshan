package com.mosheyu.controller;

import com.mosheyu.domain.Role;
import com.mosheyu.domain.User;
import com.mosheyu.service.RoleService;
import com.mosheyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/list")
    public ModelAndView list(){
        ModelAndView modelAndView = new ModelAndView();
        List<User> list =  userService.list();
        modelAndView.addObject("userList",list);
        modelAndView.setViewName("user-list");
        return modelAndView;
    }

    @RequestMapping("/saveUI")
    public ModelAndView findRole(){
        ModelAndView modelAndView = new ModelAndView();
        List<Role> list = roleService.list();
        modelAndView.addObject("roleList",list);
        modelAndView.setViewName("user-add");
        return modelAndView;
    }
    @RequestMapping("/save")
    public String save(User user,Long[] roleIds){
        userService.save(user,roleIds);
        return "redirect:/user/list";
    }

    @RequestMapping("/del/{userId}")
    public String del(@PathVariable("userId") Long userId){
        userService.del(userId);
        return "redirect:/user/list";
    }


}

