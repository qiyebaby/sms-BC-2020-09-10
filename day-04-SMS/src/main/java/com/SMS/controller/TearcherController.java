package com.SMS.controller;

import com.SMS.domain.MyClass;
import com.SMS.domain.Tearcher;
import com.SMS.domain.User;
import com.SMS.service.ClassService;
import com.SMS.service.TearcherService;
import com.SMS.service.UserService;
import com.SMS.utils.GlobalUtil;
import com.SMS.utils.Msg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: TODO //
 *
 * @Date:2020/8/29 16:11
 * @Author:何磊
 */
@Controller
@RequestMapping("/tearcher")
public class TearcherController {

    @Resource
    private TearcherService tearcherService;

    @Resource
    //在教师模块中出现用户业务处理的目的是
    //1、修改教师登录密码
    //2、新增教师信息
    private UserService userService;

    @Resource
    private ClassService classService;

    /**
     * Ajax请求查询不是班主任的教师
     */
    @RequestMapping("/query")
    @ResponseBody
    public Msg findAll(){
        List<Tearcher> tearcherList = tearcherService.findAllDept();
        return Msg.success("处理成功",tearcherList,null);
    }
    /**
     * 分页查询教师信息
     */
    @RequestMapping("/queryAll")
    @ResponseBody
    public Msg queryAll(Integer page, Integer limit, HttpSession session,String realname) {
        PageHelper.startPage(page, limit);
        List<Tearcher> tearcherList = null;

        if (realname != null){
            tearcherList = tearcherService.fuzzyQuery(realname);
        }else {
            tearcherList  = tearcherService.findAllTearcher();
        }
        //权限管理之设置密码显示为"******"
        GlobalUtil.setUserpwdToNull(session,tearcherList);
        PageInfo pageInfo = new PageInfo(tearcherList);
        return Msg.success("处理成功", tearcherList, pageInfo.getTotal());
    }

    /**
     * 修改教师信息，字段：姓名+手机号可以修改
     */
    @ResponseBody
    @RequestMapping("/modify")
    public Msg modify(Tearcher tearcher, HttpSession session, HttpServletRequest request){
       return tearcherService.modify(tearcher);
    }

    /**
     * 修改教师登录账号的登录密码
     */
    @RequestMapping("/modifyPassword")
    @ResponseBody
    public Msg modifyPassword(User user, HttpSession session, HttpServletRequest request){
        int result = userService.modifyPassword(user);
        if (result == 1){
            //成功
            return Msg.success("处理成功",null,null);
        }else {
            //失败
            return Msg.fail("修改教师密码失败");
        }
    }

    /**
     * 新增用户
     *
     */
    @RequestMapping("/save")
    @ResponseBody
    public Msg addTearcher(Tearcher tearcher, HttpSession session, HttpServletRequest request){
        return tearcherService.addTearcher(tearcher);
    }

    /**
     * 添加验证教师是否存在
     * 1、教师名相同时在验证
     * 2、手机号相同时是同一个人【填写手机号以后即发起验证】
     */
    @ResponseBody
    @RequestMapping("/exists")
    public Msg exists(String realname){
        return tearcherService.findTearcherByName(realname);
    }


    /**
     * 删除教师
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/remove")
    public Msg remove(String tids,String uids, HttpSession session, HttpServletRequest request){

        //删除之前判断该教师是不是班主任，如果是提醒用户

        String[] str_tids = tids.split(",");
        String[] str_uids = uids.split(",");
        for (String str_tid : str_tids) {
            MyClass myClass = classService.selectClassByHeadmaster(str_tid);
            if (myClass != null){//
                return  Msg.fail("删除失败，编号为"+str_tid+"的教师是班主任，请先更换班级班主任！");
            }
        }
        int result = -1;
        if (tids.contains(",")){//批量
            List<Integer> tid_list = new ArrayList<>();
            List<Long> uid_list = new ArrayList<>();

            for (String str_tid : str_tids) {
                tid_list.add(Integer.parseInt(str_tid));
            }
            for (String str_uid : str_uids) {
                uid_list.add(Integer.toUnsignedLong(Integer.parseInt(str_uid)));
            }
            result = tearcherService.removeBatch(tid_list,uid_list);
        }else {//单个
            result =tearcherService.remove(tids,uids);
        }
        if (result == -1){
            //失败
            return Msg.fail("处理失败");
        }
        //成功
        return Msg.success("处理成功",null,null);
    }
    /**
     * 根据班级编号查询班主任
     */
    @ResponseBody
    @RequestMapping("/queryheadmaster")
    public Msg queryheadmaster(Integer cno){
        if (cno == null){
            return null;
        }
        Tearcher tearcher = tearcherService.selectByCno(cno);
        return Msg.success("查询成功",null,null).add("tearcher",tearcher);
    }

}
