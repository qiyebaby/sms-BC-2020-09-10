package com.SMS.handler;

/**
 * Description: TODO //
 *
 * @Date:2020/8/27 8:42
 * @Author:何磊
 */

import com.SMS.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *  用户登录拦截器
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /**
         * 判断是否登录
         */
        HttpSession session = request.getSession(false);
        if (session == null){
            //未登录，跳转到登录页面
            response.sendRedirect(request.getContextPath()+"/login.html");
            return false;
        }
        User userInfo = (User) session.getAttribute("userInfo");
        if (userInfo == null ){
            //未登录，跳转到登录页面
            response.sendRedirect(request.getContextPath()+"/login.html");
            return false;
        }
        return true;
    }

}
