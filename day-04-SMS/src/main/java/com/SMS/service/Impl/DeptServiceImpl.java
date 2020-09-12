package com.SMS.service.Impl;

import com.SMS.dao.DeptMapper;
import com.SMS.domain.Dept;
import com.SMS.domain.DeptExample;
import com.SMS.service.DeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: TODO //
 *
 * @Date:2020/8/28 15:28
 * @Author:何磊
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptMapper deptMapper;

    @Override
    public List<Dept> findAllDept() {
        List<Dept> deptList = deptMapper.selectByExample(null);
        return deptList;
    }

    @Override
    public int addDept(Dept dept) {
        int result = deptMapper.insert(dept);
        return result;
    }

    @Override
    public int modify(Dept dept) {
        int result = deptMapper.updateByPrimaryKey(dept);
        return result;
    }

    @Override
    public int remove(String deptno) {
        int result = deptMapper.deleteByPrimaryKey(Integer.parseInt(deptno));
        return result;
    }
    //批量删除
    @Override
    public int removeBatch(List<Integer> deptno_list) {
        DeptExample example = new DeptExample();
        DeptExample.Criteria criteria = example.createCriteria();
        criteria.andDeptnoIn(deptno_list);
        int i = deptMapper.deleteByExample(example);
        return i;
    }

    @Override
    public List<Dept> fuzzyQuery(Dept dept) {
        List<Dept> deptList = new ArrayList<>();

        DeptExample example = new DeptExample();
        DeptExample.Criteria criteria = example.createCriteria();

        if (dept.getDeptno()!=null){
            Dept deptByNo = deptMapper.selectByPrimaryKey(dept.getDeptno());
            deptList.add(deptByNo);
            return deptList;
        }
        if (dept.getDeptname()!=null){
            criteria.andDeptnameLike("%"+dept.getDeptname()+"%");
        }

        List<Dept> deptListByName = deptMapper.selectByExample(example);

        return deptListByName;
    }

    @Override
    public List<Dept> findDeptByName(String deptname) {
        DeptExample example = new DeptExample();
        DeptExample.Criteria criteria = example.createCriteria();
        criteria.andDeptnameEqualTo(deptname);
        List<Dept> deptList = deptMapper.selectByExample(example);
        return deptList;
    }


}
