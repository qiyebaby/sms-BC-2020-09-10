package com.SMS.service;

import com.SMS.domain.MyClass;

import java.util.List;

/**
 * Description: TODO //
 *
 * @Date:2020/8/29 10:41
 * @Author:何磊
 */
public interface ClassService {
    List<MyClass> findAllClass();

    int addClass(MyClass myClass);

    List<MyClass> selectClassByName(String cname);

    int modify(MyClass myClass);

    List<MyClass> findByCno(MyClass myClass);

    List<MyClass> findAllDept();

    List<MyClass> selectClassDeptNo(String str_deptno);

    int removeBatch(List<Integer> cno_list);

    int remove(String cnos);

    MyClass selectClassByHeadmaster(String str_tid);

    List<MyClass> fuzzyQuery(MyClass myClass);

    List<MyClass> selectClassByDeptno(Integer deptno);

}
