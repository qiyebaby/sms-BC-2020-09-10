package com.SMS.aspect;

import com.SMS.domain.User;
import com.SMS.utils.StatisticsInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Description: TODO // 对User模块aop处理
 *
 * @Date:2020/8/31 9:55
 * @Author:何磊
 */
@Aspect
public class UserAspect {

    @Autowired
    private StatisticsInfo statisticsInfo;

    /**
     * 在用户登录时，进行环绕通知
     * 在前面读取数据库中的浏览总数，在后将新的浏览总数写入数据库
     */

    @Around("execution(* com.SMS.controller.UserController.login(..))")
    public Object doAroundStatistics(ProceedingJoinPoint pjp ) throws Throwable {
        Object res = null;
        Object[] args = pjp.getArgs();
        HttpServletRequest request = null;
        for (Object arg : args) {
            if (arg instanceof  HttpServletRequest){
                request = (HttpServletRequest) arg;
            }
        }
        //读取浏览总数据
        statisticsInfo.getStatisticsToServletContext(request);
        res = pjp.proceed();
        //写入浏览总数据
        //浏览次数+1
        statisticsInfo.statisticsAddOne(request);
        return res;
    }

    /**
     * 测试前置通知
     */
    @Before("execution(* com.SMS.controller.UserController.login(..))")
    public void sessionMap(JoinPoint joinPoint){
        HttpServletRequest request = null;
        User user = null;
        HttpSession session = null;
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest){
                request = (HttpServletRequest) arg;
            }else if (arg instanceof User){
                user = (User)arg;
            }else if (arg instanceof HttpSession){
                session = (HttpSession) arg;
            }
        }
        ServletContext servletContext = request.getServletContext();
        Map<String, HttpSession> sessionMap = (Map<String, HttpSession>) servletContext.getAttribute("sessionMap");
        if (!sessionMap.containsKey(user.getUsername())){
            //如果sessionMap中没有这个用户的登录session，则直接添加进入，如果已经有了，移除原有的，添加新的。
            sessionMap.put(user.getUsername(),session);
        }else {
            sessionMap.remove(user.getUsername());
            sessionMap.put(user.getUsername(),session);
        }

    }

}
