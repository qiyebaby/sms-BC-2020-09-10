package com.SMS.aspect;

import com.SMS.dao.AuthorityMapper;
import com.SMS.domain.Authority;
import com.SMS.domain.User;
import com.SMS.utils.Msg;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Description: TODO //权限管理使用aop完成
 *
 * @Date:2020/9/6 9:02
 * @Author:何磊
 */
@Aspect
public class AuthorityAspect {

    @Resource
    private AuthorityMapper authorityMapper;

    /**
     *      1、权限管理
     *      2、一级权限
     *          不允许删除
     *          不允许修改
     *          不允许添加
     */
    /*
        1、切入点表达式的组合使用
        2、或者 or
        3、和 and
        4、不 not
     */
    @Around("CRUD()")
    public Msg addAround(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        String methodName = signature.getName();
        String adviceInfo = "";
        if (methodName.contains("add")){
            adviceInfo = " 您没有新增的权限，请联系管理员 ";
        }else if (methodName.contains("remove")){
            adviceInfo = " 您没有删除的权限，请联系管理员 ";
        }else if (methodName.contains("modify")) {
            adviceInfo = " 您没有修改的权限，请联系管理员 ";
        }
        HttpSession session = null;
        HttpServletRequest request = null;
        System.out.println("添加方法执行了addAround============环绕通知");
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            if (arg instanceof  HttpSession){
                session = (HttpSession) arg;
            }else if (arg instanceof HttpServletRequest){
                request = (HttpServletRequest) arg;
            }
        }
        User user = (User) session.getAttribute("userInfo");
        //查询这个用户的权限
        Authority authority = authorityMapper.selectByPrimaryKey(user.getUid());
        String path = request.getRequestURI();

        if ("1".equals(authority.getLevel())){
            if (path.contains("user")||path.contains("dept")||path.contains("class")|| path.contains("room") ||path.contains("tearcher")||path.contains("student")){
                return Msg.fail("您没有此模块没有权限");
            }
        }else if ("2".equals(authority.getLevel())){
            if (methodName.contains("remove")){
                return Msg.fail(adviceInfo);
            }else if (methodName.contains("modify")){
                return Msg.fail(adviceInfo);
            }
        }else if ("3".equals(authority.getLevel())){
            if ("2".equals(user.getUsertype())){//是教师
                if (path.contains("user")||path.contains("dept")||path.contains("class")||path.contains("tearcher")){
                    return Msg.fail("您没有此模块没有权限");
                }
            }else {//不是教师，就是普通管理员了
                //没有删除的权限
                if (methodName.contains("remove")){
                    return Msg.fail(adviceInfo);
                }
            }
        }
        return (Msg) pjp.proceed();
    }




    @Pointcut("remove() || modify() || add()")
    private void CRUD() {}

    @Pointcut(value="execution(* *..controller.*.add*(..))")
    private void add(){}

    @Pointcut(value="execution(* *..controller.*.remove*(..))")
    private void remove(){}

    @Pointcut(value="execution(* *..controller.*.modify*(..))")
    private void modify(){}
}
