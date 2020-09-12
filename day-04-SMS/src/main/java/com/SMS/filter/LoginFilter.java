package com.SMS.filter;

import com.SMS.dao.GlobalInfoMapper;
import com.SMS.domain.GlobalInfo;
import com.SMS.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * Description: TODO //
 *
 * @Date:2020/8/27 9:03
 * @Author:何磊
 */
public class LoginFilter implements Filter {
    public void destroy() {
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);
        String path = request.getRequestURI();
        if (path.indexOf("login") > -1){
            //放行
            chain.doFilter(request, response);
            return;
        }
        if (session == null){
            response.sendRedirect(request.getContextPath() + "/login.html");
            return;
        }

        System.out.println("进行登录验证");
        User user = (User) session.getAttribute("userInfo");
        if (user == null){//用户不存在，说明用户session已经过期或者失效
            System.out.println("未登录");
            //非发登录，拦截所有有关资源变动的请求，对静态资源不做保护
            request.getServletContext().setAttribute("loginStatus", "您还未登录，请先进行登录");
            response.sendRedirect(request.getContextPath() + "/login.html");
        }else {
            //用户存在，
            Map<String,HttpSession> sessionMap = (Map<String, HttpSession>) request.getServletContext().getAttribute("sessionMap");
            HttpSession sessionOld = sessionMap.get(user.getUsername());
            if (sessionOld.equals(session)){//两个session相等，直接放行，这是同一个地点登录的
                //放行
                chain.doFilter(request, response);
            }else {//不相等，强制下线。
                response.sendRedirect(request.getContextPath() +"/login.html");
            }
        }
    }

}
