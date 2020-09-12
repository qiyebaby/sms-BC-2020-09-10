package com.SMS.controller;

import com.SMS.domain.MyClass;
import com.SMS.domain.Student;
import com.SMS.service.ClassService;
import com.SMS.service.StudentService;
import com.SMS.utils.AcademicBuild;
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
 * @Date:2020/8/29 10:39
 * @Author:何磊
 */
@Controller
@RequestMapping("/class")
public class ClassController {

    @Resource
    private AcademicBuild academicBuild;

    @Resource
    private ClassService classService;
    @Resource
    private StudentService studentService;
    @RequestMapping("/queryAll")
    @ResponseBody
    public Msg queryAll(Integer page,Integer limit,MyClass myClass){
        List<MyClass> classList = null;
        PageHelper.startPage(page,limit);
        if (myClass.getCname()!=null){
            classList = classService.fuzzyQuery(myClass);
        }else {
            classList = classService.findAllClass();
        }

        PageInfo pageInfo = new PageInfo(classList);
        return Msg.success("处理成功",classList,pageInfo.getTotal());
    }

    /**
     * 新增班级
     */
    @ResponseBody
    @RequestMapping("/save")
    public Msg addClass(MyClass myClass, HttpSession session, HttpServletRequest request){

        String address = AcademicBuild.toAddress(myClass.getNum(),myClass.getFloor(),myClass.getRoom());
        myClass.setAddress(address);
        List<MyClass> classList = classService.selectClassByName(myClass.getCname());
        if (classList.size() != 0){
            return Msg.fail("班级已经存在，请更换！");
        }

        if (!academicBuild.builderRoomIsAvailable(myClass)){
            return Msg.fail("选择的教学楼房间已经被占用，请更换！");
        }
        int result = classService.addClass(myClass);
        if (result == 1){
            return Msg.success("添加成功",null,null);
        }else {
            return Msg.fail("处理失败");
        }

    }

    /**
     * 查询院系名称是否存在
     */
    @RequestMapping("/byname")
    @ResponseBody
    public Msg selectClassByName(String cname){
        List<MyClass> classList = classService.selectClassByName(cname);
        if (classList.size() == 0){
            //不存在，可以使用
            return  Msg.success("院系名称可以使用",null,null);
        }else {
            //已经存在
            return Msg.fail("院系名已经存在，请更换！");
        }
    }

    /**
     * 修改班级信息
     */
    @ResponseBody
    @RequestMapping("/modify")
    public Msg modify(MyClass myClass, HttpSession session, HttpServletRequest request){

        List<MyClass> classList = classService.findByCno(myClass);
        MyClass myClass1 = classList.get(0);

        String address = AcademicBuild.toAddress(myClass.getNum(),myClass.getFloor(),myClass.getRoom());
        myClass.setAddress(address);
        if (!address.equals(myClass1.getAddress())){
            if (!academicBuild.builderRoomIsAvailable(myClass)){
                return Msg.fail("选择的教学楼房间已经被占用，请更换！");
            }
        }
        myClass.setAddress(null);
        int result = classService.modify(myClass);
        if (result == 1){
            return  Msg.success("处理成功",null,null);
        }
        return Msg.fail("处理失败");
    }

    /**
     * 定义直接查询所有班级名的方法，目的是为了选择选择班级【在注册学生的时候】
     * 目的是展示在select中
     */
    @ResponseBody
    @RequestMapping("/query")
    public Msg findAll(){
        List<MyClass> myClassList  = classService.findAllDept();
        return Msg.success("处理成功",myClassList,null);
    }


    /**
     * 删除班级
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/remove")
    public Msg removeDept(String cnos, HttpSession session, HttpServletRequest request){

        //删除之前判断该班级还有学生，如果有不允许删除院系。

        String[] str_cnos = cnos.split(",");
        for (String str_cno : str_cnos) {
            List<Student> studentList  = studentService.selectStudentCno(str_cno);
            if (studentList.size() != 0){//
                return  Msg.fail("删除失败，编号为"+str_cno+"的班级尚有学生！");
            }
        }
        int result = -1;
        if (cnos.contains(",")){//批量
            List<Integer> cno_list = new ArrayList<>();
            for (String str_cno : str_cnos) {
                cno_list.add(Integer.parseInt(str_cno));
            }
            result = classService.removeBatch(cno_list);
        }else {//单个
            result =classService.remove(cnos);
        }
        if (result == -1){
            //失败
            return Msg.fail("处理失败");
        }
        //成功
        return Msg.success("处理成功",null,null);
    }

    /**
     * 根据院系编号查询班级
     */
    @RequestMapping("/queryClass")
    @ResponseBody
    public Msg queryClass(Integer deptno){

        if (deptno == null){
            return null;
        }
        List<MyClass> classList = classService.selectClassByDeptno(deptno);

        return Msg.success("成功",classList,null);
    }


}
