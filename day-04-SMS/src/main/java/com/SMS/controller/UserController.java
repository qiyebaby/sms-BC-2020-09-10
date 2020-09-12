package com.SMS.controller;

import com.SMS.aspect.UserAspect;
import com.SMS.dao.GlobalInfoMapper;
import com.SMS.domain.GlobalInfo;
import com.SMS.domain.User;
import com.SMS.service.UserService;
import com.SMS.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description: TODO //
 *
 * @Date:2020/8/26 10:05
 * @Author:何磊
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *  用户登录系统的验证，系统的登录判断
     * @param user  前端页面获取到的用户名，用户密码，用户类型
     * @param session   session会话状态，目的是将登录信息存储到session中
     * @param request   获取项目名+浏览次数+1需要request
     * @return  登录处理的状态结果返回给前端
     */
    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Msg login(User user, HttpSession session, HttpServletRequest request) {

        User userInfo =  userService.login(user);
        if (userInfo != null){//账号存在

            //判断该用户状态，是否已经被禁止登录
            if ("0".equals(userInfo.getStatus())){//用户已经被禁止登录
                return Msg.fail("您已经被禁止登录，请联系管理员");
            }

            //程序到这，说明该用户存在，且属于可用状态。将登录状态保存到session中
            session.setAttribute("userInfo",userInfo);

        }else {//假-登录失败
            return Msg.fail("登录失败，请检查用户名，密码，用户类型");
        }
        return Msg.success("成功",null,null).add("url",request.getContextPath()+"/");
    }


    /**
     * 获取首页的信息，有多少用户，有多少教师，有多少学生
     * @return
     */
    @ResponseBody
    @RequestMapping("getIndexInfo")
    public Msg getIndexInfo(){
        List<User> userList = userService.findUsers();
        //获取总用户数
        int total = userList.size();
        int total_student = 0;
        int total_tearcher = 0 ;
        for (User user : userList) {
            if ("1".equals(String.valueOf(user.getUsertype()))){
                total_student++;
            }else if ("2".equals(String.valueOf(user.getUsertype()))){
                total_tearcher++;
            }
        }
        return Msg.success("处理成功！",null,null).add("userList",userList).
                add("total",total).add("total_student",total_student).
                add("total_tearcher",total_tearcher);
    }

    /**
     * 分页查询用户信息，就是展示给用户的信息
     * @param page  当前第几页
     * @param limit 每页显示几条数据
     * @return
     */
    @RequestMapping("/queryAll")
    @ResponseBody
    public Msg queryAll(Integer page,Integer limit,HttpSession session,User user) {
        PageHelper.startPage(page, limit);
        List<User> list = null;

        if (user.getUsername()!=null
                ||user.getStatus()!=null && user.getStatus()!=""
                ||user.getUsertype()!=null && user.getUsertype()!=""){
            list = userService.fuzzyQuery(user);
        }else {
            list = userService.findAllUser();
        }
        //权限管理之设置密码显示为"******"
        GlobalUtil.setUserpwdToNull(session,list);
        PageInfo pageInfo = new PageInfo(list);
        return Msg.success("处理成功", list, pageInfo.getTotal());
    }

    /**
     *  进行用户状态的修改。
     * @param uid   需要修改的用户的uid
     * @param status    修改的状态
     * @return
     */
    @RequestMapping("/status")
    @ResponseBody
    public Msg modifyStatus(String uid,String status, HttpSession session, HttpServletRequest request){
        int result = userService.modifyStatus(uid,status);
        if (result == 1){
            return Msg.success("处理成功", null, null) ;
        }else {
            return Msg.fail("处理失败");
        }
    }

    /**
     * 用户新增
     */
    @RequestMapping("/save")
    @ResponseBody
    public Msg addUser(User user, HttpSession session, HttpServletRequest request){

        //新增之前判断用户是否已经存在
        boolean result1 = userService.findUserByName(user.getUsername(),user.getUsertype());
        if (!result1){
            return Msg.fail("用户已经存在，请更换登录用户名");
        }
        user.setStatus("1");
        user.setDate(DateUtil.getTime(Const.TIME_FORMAT_TWO));
        int result = userService.addUser(user);
        if (result == 1){//成功
            return Msg.success("新增成功",null,null);
        }else {//失败
            return Msg.fail("新增失败");
        }
    }

    /**
     * 判断用户是否已经存在
     */
    @ResponseBody
    @RequestMapping("/exists")
    public Msg exists(String username,String usertype){

        if (username.trim() == ""){
            return Msg.fail("用户名不能为空，请填写登录用户名");
        }

        boolean result = userService.findUserByName(username,usertype);
        if (!result){
            return Msg.fail("用户已经存在，请更换登录用户名");
        }
        return Msg.success("登录账号可用",null,null);
    }

    /**
     * 修改用户信息 包括用户登录密码+用户类型
     */
    @RequestMapping("/modify")
    @ResponseBody
    public Msg modify(User user,HttpSession session, HttpServletRequest request){

       int result =  userService.modify(user);

       boolean isSame = false;
       if (result == 1){//成功
           //判断当前修改账户是否是当前登录账户，如果是则移除当前登录状态，请用户重新登录
            User userInfo = (User) session.getAttribute("userInfo");
            if (userInfo.getUsername().equals(user.getUsername())){
                //是同一个用户
                isSame = true;
//              移除当前登录状态
                session.removeAttribute("userInfo");
            }
            return  Msg.success("修改成功",null,null).add("isSame",isSame);
       }else {//失败
            return Msg.fail("修改用户信息失败");
       }
    }

    /**
     * 删除用户
     */
    @ResponseBody
    @RequestMapping("/remove")
    public Msg remove(String uids, HttpSession session, HttpServletRequest request){
        if (uids.equals("1")){//这个是系统管理员，无法删除
            return Msg.fail("删除个鬼哦，劳资是系统管理员！");
        }
        String[] str_uids = uids.split(",");
        int result = -1;
        if (uids.contains(",")){
            return userService.removeBatch(str_uids);
        }else {//删除单个用户
            return userService.remove(uids);
        }
    }
}
