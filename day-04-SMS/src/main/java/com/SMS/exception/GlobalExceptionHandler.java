package com.SMS.exception;

import com.SMS.utils.Msg;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Description: TODO // 全局异常处理类
 *
 * @Date:2020/9/3 16:21
 * @Author:何磊
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Msg globalException(Exception e, HttpServletResponse response){
        e.printStackTrace();
        return Msg.fail("系统出现异常信息,请联系管理员！！！");
    }

}
