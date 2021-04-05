package com.mosheyu.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionTest2 implements HandlerExceptionResolver {
    /*
        Exception 异常对象
        ModelAndView 跳转的错误视图信息

     */
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView();

        if(e instanceof ExceptionTest1){
            modelAndView.addObject("news","1111自定义异常。");
        }else if (e instanceof ClassCastException){
            modelAndView.addObject("news","2222222222222222222类型转换异常");
        }
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
