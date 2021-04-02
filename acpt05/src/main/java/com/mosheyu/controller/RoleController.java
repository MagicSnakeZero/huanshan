package com.mosheyu.controller;


import com.mosheyu.domain.Role;
import com.mosheyu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {


    @Autowired
    private RoleService roleServe;

    public void setRoleServe(RoleService roleServe) {
        this.roleServe = roleServe;
    }

    @RequestMapping("/list")
    public ModelAndView list(){
        List<Role> list = roleServe.list();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("roleList",list);
        modelAndView.setViewName("role-list");
        return modelAndView;
    }

    @RequestMapping("save")
    public String save(Role role){
        roleServe.save(role);
        return "redirect:/role/list";
    }
}

