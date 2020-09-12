package com.SMS.service.Impl;

import com.SMS.dao.MyClassMapper;
import com.SMS.domain.Dept;
import com.SMS.domain.DeptExample;
import com.SMS.domain.MyClass;
import com.SMS.domain.MyClassExample;
import com.SMS.service.ClassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: TODO //
 *
 * @Date:2020/8/29 10:43
 * @Author:何磊
 */
@Service
public class ClassServiceImpl implements ClassService {

    @Resource
    private MyClassMapper classMapper;

    @Override
    public List<MyClass> findAllClass() {
        List<MyClass> classList = classMapper.selectAllClass();
        return classList;
    }

    @Override
    public int addClass(MyClass myClass) {
        int result = classMapper.insert(myClass);
        return result;
    }

    @Override
    public List<MyClass> selectClassByName(String cname) {

        MyClassExample example = new MyClassExample();
        MyClassExample.Criteria criteria = example.createCriteria();
        criteria.andCnameEqualTo(cname);
        List<MyClass> classList = classMapper.selectByExample(example);
        return classList;
    }

    @Override
    public int modify(MyClass myClass) {

        MyClassExample example = new MyClassExample();
        MyClassExample.Criteria criteria = example.createCriteria();
        criteria.andCnoEqualTo(myClass.getCno());
        if (myClass.getDeptno() == 0){
            myClass.setDeptno(null);
        }
        if (myClass.getHeadmaster() == 0){
            myClass.setHeadmaster(null);
        }
        System.out.println(myClass.getAddress());
        System.out.println(myClass.getDeptno());
        System.out.println(myClass.getHeadmaster());
        int result = classMapper.updateByExample(myClass, example);
        return result;
    }

    @Override
    public List<MyClass> findByCno(MyClass myClass) {

        MyClassExample example = new MyClassExample();
        MyClassExample.Criteria criteria = example.createCriteria();
        criteria.andCnoEqualTo(myClass.getCno());


        List<MyClass> classList = classMapper.selectByExample(example);
        return classList;
    }

    @Override
    public List<MyClass> findAllDept() {
        return  classMapper.selectByExample(null);
    }


    /**
     * 通过院系id查询班级
     * @param str_deptno
     * @return
     */
    @Override
    public List<MyClass> selectClassDeptNo(String str_deptno) {

        MyClassExample example = new MyClassExample();
        MyClassExample.Criteria criteria = example.createCriteria();
        criteria.andDeptnoEqualTo(Integer.parseInt(str_deptno));

        List<MyClass> classList = classMapper.selectByExample(example);
        return classList;
    }


    /**
     * 批量删除班级
     * @param cno_list
     * @return
     */
    @Override
    public int removeBatch(List<Integer> cno_list) {

        MyClassExample example = new MyClassExample();
        MyClassExample.Criteria criteria = example.createCriteria();

        criteria.andCnoIn(cno_list);

        return  classMapper.deleteByExample(example);
    }

    /**
     * 删除单个班级信息
     * @param cnos
     * @return
     */
    @Override
    public int remove(String cnos) {
        return classMapper.deleteByPrimaryKey(Integer.parseInt(cnos));
    }


    /**
     * 根据班主任查询班级，目的是为了判断删除的教师是不是班主任
     * @param str_tid
     * @return
     */
    @Override
    public MyClass selectClassByHeadmaster(String str_tid) {

        MyClass myClass = classMapper.selectByHeadmaster(Integer.parseInt(str_tid));

        return myClass;
    }

    @Override
    public List<MyClass> fuzzyQuery(MyClass myClass) {

        MyClassExample example = new MyClassExample();
        MyClassExample.Criteria criteria = example.createCriteria();
        if (myClass.getCname()!=null){
            criteria.andCnameLike("%"+myClass.getCname()+"%");
            return classMapper.selectByExample(example);
        }
        return null;
    }

    @Override
    public List<MyClass> selectClassByDeptno(Integer deptno) {
        MyClassExample example = new MyClassExample();
        MyClassExample.Criteria criteria = example.createCriteria();
        criteria.andDeptnoEqualTo(deptno);
        List<MyClass> classList = classMapper.selectByExample(example);
        return classList;
    }
}
