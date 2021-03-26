package com.mosheyu.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //将Spring的应用上下文对象，存储到ServletContext域中。
        ServletContext servletContext = servletContextEvent.getServletContext();
        //读取web.xml中的参数
        String contextConfigLocation = servletContext.getInitParameter("contextConfigLocation");
        ApplicationContext app = new ClassPathXmlApplicationContext(contextConfigLocation);
        servletContext.setAttribute("app",app);
        System.out.println("创建应用上下文并存储到ServletContext域中。");

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
