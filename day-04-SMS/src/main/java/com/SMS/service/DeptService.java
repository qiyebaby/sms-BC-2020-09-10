package com.SMS.service;

import com.SMS.domain.Dept;

import java.util.List;

/**
 * Description: TODO //
 *
 * @Date:2020/8/28 15:27
 * @Author:何磊
 */
public interface DeptService {
    List<Dept> findAllDept();

    int addDept(Dept dept);

    int modify(Dept dept);

    int remove(String deptno);

    List<Dept> findDeptByName(String deptname);

    int removeBatch(List<Integer> deptnos);

    List<Dept> fuzzyQuery(Dept dept);

}
