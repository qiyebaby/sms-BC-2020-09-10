package com.SMS.aspect;

import com.SMS.domain.User;
import com.SMS.utils.Msg;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Description: TODO // 全局的aspectJ，针对共有特点的切面
 *
 * @Date:2020/9/2 8:34
 * @Author:何磊
 */

public class GlobalAspect {


    /**
     *  1、分析共工切面：用户新增前验证用户信息
     *  2、使用前置通知进行用户信息验证
     */
    @Around("execution(* *..*.add*(..))")
    public Msg exists_info(ProceedingJoinPoint pjp) throws Throwable {
        //前：判断是否存在
        Msg res = null;
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            if (arg instanceof User){
                User user = (User) arg;
                //新增之前判断用户是否已经存在
                return Msg.fail("用户已经存在，请更换登录用户名");
//                boolean result1 = userService.findUserByName(user.getUsername(),user.getUsertype());
//                if (!result1){
//                    return Msg.fail("用户已经存在，请更换登录用户名");
//                }
            }

        }
        res = (Msg) pjp.proceed();
        return res;
    }


}
