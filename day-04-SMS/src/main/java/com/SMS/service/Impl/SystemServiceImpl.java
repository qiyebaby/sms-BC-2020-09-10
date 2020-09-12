package com.SMS.service.Impl;

import com.SMS.dao.AuthorityMapper;
import com.SMS.dao.StudentMapper;
import com.SMS.dao.TearcherMapper;
import com.SMS.dao.UserMapper;
import com.SMS.domain.*;
import com.SMS.service.SystemService;
import com.SMS.service.UserService;
import com.SMS.utils.Msg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: TODO //
 *
 * @Date:2020/9/7 13:49
 * @Author:何磊
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private TearcherMapper tearcherMapper;

    @Resource
    private AuthorityMapper authorityMapper;

    @Override
    public Msg administrator(User user) {

        Authority authority = authorityMapper.selectByPrimaryKey(user.getUid());
        User userInfo = userMapper.selectByPrimaryKey(user.getUid());
        return Msg.success("查询管理员信息成功",null,null).
                add("authority",authority).add("userInfo",userInfo);
    }

    @Override
    public Msg student(User user) {
        Authority authority = authorityMapper.selectByPrimaryKey(user.getUid());
        User userInfo = userMapper.selectByPrimaryKey(user.getUid());

        StudentExample example = new StudentExample();
        StudentExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(user.getUid());
        List<Student> studentList = studentMapper.selectByExample(example);
        Student student = studentList.get(0);
        return Msg.success("查询学生信息成功",null,null).
                add("authority",authority).add("userInfo",userInfo).add("student",student);
    }

    @Override
    public Msg tearcher(User user) {
        Authority authority = authorityMapper.selectByPrimaryKey(user.getUid());
        User userInfo = userMapper.selectByPrimaryKey(user.getUid());

        TearcherExample example = new TearcherExample();
        TearcherExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(user.getUid());
        List<Tearcher> tearcherList = tearcherMapper.selectByExample(example);
        Tearcher tearcher = tearcherList.get(0);

        return Msg.success("查询教师信息成功",null,null).
                add("authority",authority).add("userInfo",userInfo).add("tearcher",tearcher);
    }

    @Override
    public int modify(Long uid, String new_password) {
        User user = new User();
        user.setUid(uid);
        user.setUserpwd(new_password);
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
