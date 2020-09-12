package com.SMS.controller;

import com.SMS.domain.Student;
import com.SMS.domain.User;
import com.SMS.service.StudentService;
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
 * @Date:2020/8/30 10:09
 * @Author:何磊
 */
@Controller
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;

    @Resource
    private UserService userService;

    /**
     * 分页查询学生信息
     */
    @RequestMapping("/queryAll")
    @ResponseBody
    public Msg queryAll(Integer page, Integer limit, HttpSession session,Student student) {
        PageHelper.startPage(page, limit);
        List<Student> list = null;

        if (student.getSno()!=null||student.getSname()!=null||student.getSex()!=null||student.getCname()!=null){
            list = studentService.fuzzyQuery(student);
        }else {
            list = studentService.findAllClass();
        }

        //权限管理之设置密码显示为"******"
        GlobalUtil.setUserpwdToNull(session,list);

        PageInfo pageInfo = new PageInfo(list);
        return Msg.success("处理成功", list, pageInfo.getTotal());
    }
    /**
     * 添加验证学生是否存在
     * 1、学号不能相同
     */
    @ResponseBody
    @RequestMapping("/exists")
    public Msg exists(String sno){
        return studentService.findStudentByName(sno);
    }

    /**
     * save
     *  1、保存学生信息
     */
    @ResponseBody
    @RequestMapping("/save")
    public Msg addStudent(Student student, HttpSession session, HttpServletRequest request){

        return studentService.addStudent(student);
    }

    /**
     * 修改学生信息
     */
    @ResponseBody
    @RequestMapping("/modify")
    public Msg modify(Student student, HttpSession session, HttpServletRequest request){
        return studentService.modify(student);
    }

    /**
     * 修改学生信息登录账号的登录密码
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
     * 删除学生
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/remove")
    public Msg remove(String sids,String uids, HttpSession session, HttpServletRequest request){
        String[] str_sids = sids.split(",");
        String[] str_uids = uids.split(",");
        int result = -1;
        if (uids.contains(",")){//批量
            List<Integer> sid_list = new ArrayList<>();
            List<Long> uid_list = new ArrayList<>();
            for (String str_sid : str_sids) {
                studentService.isOnlyPeople(str_sid);
                sid_list.add(Integer.parseInt(str_sid));
            }
            for (String str_uid : str_uids) {
                uid_list.add(Integer.toUnsignedLong(Integer.parseInt(str_uid)));
            }
            result = studentService.removeBatch(sid_list,uid_list);
        }else {//单个

            studentService.isOnlyPeople(sids);

            result =studentService.remove(sids,uids);
        }
        if (result == -1){
            //失败
            return Msg.fail("处理失败");
        }
        //成功
        return Msg.success("处理成功",null,null);
    }

    /**
     * 根据班级查询学生信息，本班学生，目的是做通知管理
     */

    @RequestMapping("/querycno")
    @ResponseBody
    public Msg querycno(Integer cno){
        if (cno == null){
            return null;
        }
        List<Student> studentList = studentService.selectByCno(cno);
        return Msg.success("成功",studentList,null);
    }




}
