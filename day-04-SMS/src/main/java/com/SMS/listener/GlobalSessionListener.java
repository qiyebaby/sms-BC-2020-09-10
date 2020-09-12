package com.SMS.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Description: TODO //
 *
 * @Date:2020/9/4 21:13
 * @Author:何磊
 */
public class GlobalSessionListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //初始化的时候创建一个map集合。目的是存放登录信息
        //这个登录信息里面存放的是当前登录账号的session信息

        //这个map的key就是登录账号的用户名
        //value就是session
        System.out.println("==================创建ServletContext");
        System.out.println("==================创建ServletContext");
        System.out.println("==================创建ServletContext");
        Map<String, HttpSession> sessionMap = new HashMap<>();
        ServletContext servletContext = servletContextEvent.getServletContext();

        servletContext.setAttribute("sessionMap",sessionMap);

    }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //服务器关闭，清空所有session信息
        System.out.println("==================销毁ServletContext");
        System.out.println("==================销毁ServletContext");
        System.out.println("==================销毁ServletContext");
        ServletContext servletContext = servletContextEvent.getServletContext();
        Map<String,HttpSession> sessionMap = (Map<String, HttpSession>) servletContext.getAttribute("sessionMap");
        Set<String> sessionMaps = sessionMap.keySet();
        for (String key : sessionMaps) {
            HttpSession httpSession = sessionMap.get(key);
            //安全退出
            httpSession.invalidate();
        }
    }
}
