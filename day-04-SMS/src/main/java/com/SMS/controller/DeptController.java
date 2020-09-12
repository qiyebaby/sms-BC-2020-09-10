package com.SMS.controller;

import com.SMS.domain.Dept;
import com.SMS.domain.MyClass;
import com.SMS.domain.User;
import com.SMS.service.ClassService;
import com.SMS.service.DeptService;
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
 * @Date:2020/8/28 15:24
 * @Author:何磊
 */
@Controller
@RequestMapping("/dept")
public class DeptController {

    @Resource
    private DeptService deptService;

    @Resource
    private ClassService classService;
    /**
     * 分页查询院系信息，目的是展示在查询院系的首页（分页查询）
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/queryAll")
    @ResponseBody
    public Msg findAllDept(Integer page,Integer limit,Dept dept){
        PageHelper.startPage(page,limit);
        List<Dept> deptList = null;
        if (dept.getDeptno()!=null||dept.getDeptname()!=null){
            /**
             * 模糊查询院系信息
             */
            deptList =  deptService.fuzzyQuery(dept);
        }else {//这个就是没有参数的分页查询
            deptList  = deptService.findAllDept();
        }
        PageInfo pageInfo = new PageInfo(deptList);
        return Msg.success("处理成功",deptList,pageInfo.getTotal());
    }



    /**
     * 新增院系
     * @param dept
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public Msg addDept(Dept dept, HttpSession session, HttpServletRequest request){

        //用户是否存在的验证
        List<Dept> deptList = deptService.findDeptByName(dept.getDeptname());
        if (deptList.size() == 0){//没有此用户名
            int result = deptService.addDept(dept);
            if (result == 1){
                //成功
                return Msg.success("处理成功",null,null);
            }else {
                //失败
                return Msg.fail("处理失败");
            }
        }else {//用户名已经存在
            return Msg.fail("处理失败");
        }


    }

    /**
     * 修改院系信息
     * @param dept
     * @return
     */
    @RequestMapping("/modify")
    @ResponseBody
    public Msg modifyDept(Dept dept, HttpSession session, HttpServletRequest request){
        int result = deptService.modify(dept);
        if (result == 1){
            //成功
            return Msg.success("处理成功",null,null);
        }else {
            //失败
            return Msg.fail("处理失败");
        }
    }

    /**
     * 删除院系                     【带改进------批量删除的修改】
     * @param deptnos
     * @return
     */
    @ResponseBody
    @RequestMapping("/remove")
    public Msg removeDept(String deptnos, HttpSession session, HttpServletRequest request){

        //删除之前判断该院系是否有班级，有班级提示用户还有班级，没班级直接删除

        String[] str_deptnos = deptnos.split(",");
        for (String str_deptno : str_deptnos) {
            //str_deptno院系名
            //如果有一个院系名不行直接返回false通知用户
            List<MyClass> myClassList = classService.selectClassDeptNo(str_deptno);
            if (myClassList.size() != 0){//查询到院系了，不可以删除
                return  Msg.fail("删除失败，编号为"+str_deptno+"的院系尚有班级！");
            }
        }

        int result = -1;
        if (deptnos.contains(",")){//批量
            List<Integer> deptno_list = new ArrayList<>();
            for (String str_deptno : str_deptnos) {
                deptno_list.add(Integer.parseInt(str_deptno));
            }
            result = deptService.removeBatch(deptno_list);
        }else {//单个
            result =deptService.remove(deptnos);
        }
        if (result == -1){
            //失败
            return Msg.fail("处理失败");
        }

        System.out.println(result);


        //成功
        return Msg.success("处理成功",null,null);
    }

    /**
     * 当前院系是否已经存在
     * @param deptname
     * @return
     */
    @RequestMapping("/byname")
    @ResponseBody
    public Msg findDeptByName(String deptname){
       List<Dept> deptList = deptService.findDeptByName(deptname);
       if (deptList.size() == 0){//没有此用户名
            return Msg.success("该院系名可用",null,null);
       }else {//用户名已经存在
           return Msg.fail("该院系名已经存在");
       }
    }


    /**
     * 定义直接查询所有院系名的方法，目的是为了选择选择院系
     * 目的是展示在select中
     */
    @ResponseBody
    @RequestMapping("/query")
    public Msg findAll(){
        List<Dept> deptList = deptService.findAllDept();
        return Msg.success("处理成功",deptList,null);
    }



}
