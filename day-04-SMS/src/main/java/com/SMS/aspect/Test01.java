package com.SMS.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Description: TODO //
 *
 * @Date:2020/9/1 17:16
 * @Author:何磊
 */
@Aspect
public class Test01 {
    /**
     * 测试前置通知是否对service有效
     */
    @Before("execution(* com.SMS.controller.UserController.login(..))")
    public void doBefore(JoinPoint joinPoint){
    }

}
