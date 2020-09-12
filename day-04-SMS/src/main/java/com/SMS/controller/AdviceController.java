package com.SMS.controller;

import com.SMS.domain.Advice;
import com.SMS.domain.Student;
import com.SMS.domain.Tearcher;
import com.SMS.domain.User;
import com.SMS.service.AdviceService;
import com.SMS.service.StudentService;
import com.SMS.service.TearcherService;
import com.SMS.utils.Msg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/*
    1、通知模块分析
        - 用户登录以后自动进行提示，如果有通知信息提示有通知信息图标
        - 如果是必读信息，直接弹窗
        - 管理员发布公告，直接弹窗

        通知内容有强度划分
        【系统管理员发布】四级--->【读取】 普通管理员，学生，教师

        【普通管理员发布】三级--->【读取】 学生，教师

        【教师发布】二级--->【读取】学生   ----二级是必读信息，直接弹窗
                        必须只有班主任才可以发布，发布对象在具体就是本班同学
        【教师发布】一级--->【读取】学生   ----一级不是必读信息，有勋章提示红点即可【学生点击查看】

设计表 t_advice
字段：
    aid(主键，自增) level(通知等级) content(通知内容) data(记录通知时间) uid(谁通知的)
    1               1             放学来办公室一趟   2020-09-07         20
    2               4              明天全校开会      2020-09-07         1

    用户登录以后进行检测，检测通知信息

    1、如果是admin    无需检测

    2、如果是普通管理员   检测是否有四级通知，有的话直接弹窗

    3、如果是教师，先检测四级通知，完毕以后在检测三级通知

    4、如果是学生，检测四级，三级，二级都直接弹窗，如果是一级进行设置通知信息红点

 */
@Controller
@RequestMapping("/advice")
public class AdviceController {

    @Resource
    private AdviceService adviceService;

    @Resource
    private StudentService studentService;

    @Resource
    private TearcherService tearcherService;

    /**
     * 通知单人-教师或者学生
     * @param advice
     * @return
     */
    @ResponseBody
    @RequestMapping("/single")
    public Msg single(Advice advice,Integer no,HttpSession session){
        //看你谁在通知，不是管理员就是教师
        User user = (User) session.getAttribute("userInfo");
        if ("0".equals(user.getUsertype())) {//管理员
            user.getUsertype();
            if (user.getUid() == 1){
                advice.setPerson("系统管理员");
            }else {
                advice.setPerson("普通管理员");
                if (advice.getLevel() == 5 ){
                    return Msg.fail("您没有发布五级通知的权限！");
                }
            }
        }else if("1".equals(user.getUsertype())){//学生
            return Msg.fail("作为学生，您没有发布通知的权限！");
        }else {//教师
            if (advice.getLevel() > 3 ){
                return Msg.fail("您没有发布大于三级通知的权限！");
            }
            Tearcher tearcher = tearcherService.selectByUid(user.getUid());
            advice.setPerson(tearcher.getRealname());
        }
        //管你通知谁，我只需要写入通知信息表?但是需要一个uid
        Long uid = null;
        //判断是学生还是教师
        if ("1".equals(advice.getUsertype())){
            //去学生表查询学生的uid
            Student student = studentService.selectBySid(no);
            uid = student.getUid();
        }else if ("2".equals(advice.getUsertype())){
            //去教师表查教师的uid
            Tearcher tearcher = tearcherService.selectBytid(no);
            uid = tearcher.getUid();
        }
        advice.setUid(uid);
        int result = adviceService.addSingle(advice);
        if (result == 1){
            return Msg.success("通知成功",null,null);
        }
        return Msg.fail("新增通知信息失败");
    }


    //ajax做全局通知处理，目的是打开页面进行消息通知
    @RequestMapping("/global_advice")
    @ResponseBody
    public Msg global_advice(HttpSession session){
        User user = (User) session.getAttribute("userInfo");
        //0、判断登录是不是admin
        if (user.getUid() == 1){
            return Msg.fail("系统管理员无通知");
        }
        int count = 0;
        //通知等级
        List<Advice> level_five = null;
        List<Advice> level_four = null;
        List<Advice> level_three = null;
        List<Advice> level_two = null;
        List<Advice> level_one = null;
        //1、判断是否有五级通知,全局通知
        List<Advice> adviceByFive = adviceService.findAdviceByFive();
        if (adviceByFive.size() != 0){//有五级通知
            level_five = adviceByFive;
        }

        if ("1".equals(user.getUsertype())){
            //看看有没有登录账号的一级通知-----专门给学生
            List<Advice> adviceByOne =  adviceService.findAdviceByOne(user.getUid());
            if (adviceByOne.size() != 0 ){
                level_one = adviceByOne;
                count = count+level_one.size();
            }
            //首先看有没有全体学生的通知信息-三级
            List<Advice> adviceByThree = adviceService.findAdviceByThree();
            if (adviceByThree.size() != 0){//有三级
                level_three = adviceByThree;
                count = count+level_three.size();
            }
            return Msg.success("通知处理成功",null,null).
                    add("level_five",level_five).
                    add("level_two",level_two).
                    add("level_one",level_one).
                    add("level_three",level_three).add("count",count);
        }else if ("2".equals(user.getUsertype())){

            //看看有没有登录账号的二级通知-------专门给教师
            List<Advice> adviceByTwo =  adviceService.findAdviceByTwo(user.getUid());
            if (adviceByTwo.size() != 0 ){
                level_two = adviceByTwo;
                count = count+level_two.size();
            }
            //首先看有没有全体教师的通知信息-四级通知
            List<Advice> adviceByFour = adviceService.findAdviceByFour();
            if (adviceByFour.size() != 0){//有四级通知
                level_four = adviceByFour;
                count = count+level_four.size() ;
            }
            return Msg.success("通知处理成功",null,null).
                    add("level_five",level_five).
                    add("level_two",level_two).
                    add("level_one",level_one).
                    add("level_four",level_four).
                    add("count",count);
        }
        return Msg.success("查询通过",null,null).
                add("level_five",level_five);
    }
    /**
     * 通知全体
     */
    @RequestMapping("/all")
    @ResponseBody
    public Msg advice_all(Advice advice,Integer no){

        if (no == 0){
            return Msg.fail("您选择的是通知单人");
        }else if (no == 1){
            advice.setLevel(3);
        }else if (no == 2){
            advice.setLevel(4);
        }else if (no == 3){
            advice.setLevel(5);
        }

        int result = adviceService.addSingle(advice);

        if (result != 1){
            return Msg.fail("新增通知失败");
        }
        return Msg.success("新增通知成功",null,null);
    }

    /**
     * 删除通知
     */
    @ResponseBody
    @RequestMapping(value = "/deleteadvice",method = RequestMethod.POST)
    public Msg deleteadvice(Integer aid){
        int result = adviceService.remove(aid);
        if (result != 1){
            return Msg.fail("删除失败");
        }
        return Msg.success("删除成功",null,null);
    }

}
