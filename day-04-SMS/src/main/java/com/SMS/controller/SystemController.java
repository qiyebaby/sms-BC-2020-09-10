package com.SMS.controller;

import com.SMS.domain.User;
import com.SMS.service.SystemService;
import com.SMS.utils.Msg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Description: TODO //
 *
 * @Date:2020/9/7 13:39
 * @Author:何磊
 */
@Controller
@RequestMapping("/system")//系统信息处理模块，防止拦截修改事件
public class SystemController {

    @Resource
    private SystemService systemService;
    @RequestMapping("/query")
    @ResponseBody
    public Msg query(HttpSession session){
        User user = (User) session.getAttribute("userInfo");
        if ("0".equals(user.getUsertype())){//登录账号为管理员
            return systemService.administrator(user);
        }else if ("1".equals(user.getUsertype())){//登录账号为学生
            return systemService.student(user);
        }else if ("2".equals(user.getUsertype())){////登录账号为教师
            return systemService.tearcher(user);
        }
        return Msg.fail("失败");
    }
    /**
     * 校验旧密码是否正确
     * @param old_password
     * @return
     */
    @RequestMapping(value = "/validate",method = RequestMethod.POST)
    @ResponseBody
    public Msg validate(String old_password,HttpSession session){
        User user = (User) session.getAttribute("userInfo");

        if (!old_password.equals(user.getUserpwd())){
            return Msg.fail("您填写的旧密码不正确，请重新输入");
        }
        return Msg.success("正确",null,null);
    }

    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Msg update(String old_password,
                      String new_password,
                      String again_password,HttpSession session){
        User user = (User) session.getAttribute("userInfo");
        if (!old_password.equals(user.getUserpwd())){
            return Msg.fail("您填写的旧密码不正确，请重新输入");
        }
        if (!new_password.equals(again_password)){
            return Msg.fail("两次输入密码不一致，请重新输入");
        }
        int result = systemService.modify(user.getUid(),new_password);

        if (result == 1){

            //修改成功，您已经修改了密码，移除登录状态
            //移除当前登录状态
            session.removeAttribute("userInfo");
            return Msg.success("修改成功",null,null);
        }
        return Msg.fail("修改失败，请联系管理员");
    }

    /**
     *  退出登录【安全退出】
     */
    @RequestMapping(value = "/loginout",method = RequestMethod.POST)
    @ResponseBody
    public Msg loginout(HttpSession session){
        session.removeAttribute("userInfo");
        session.invalidate();
        return Msg.fail("安全退出成功");
    }
}
