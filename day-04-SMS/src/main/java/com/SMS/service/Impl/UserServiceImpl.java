package com.SMS.service.Impl;

import com.SMS.dao.AuthorityMapper;
import com.SMS.dao.UserMapper;
import com.SMS.domain.Authority;
import com.SMS.domain.Tearcher;
import com.SMS.domain.User;
import com.SMS.domain.UserExample;
import com.SMS.service.StudentService;
import com.SMS.service.TearcherService;
import com.SMS.service.UserService;
import com.SMS.utils.Msg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: TODO //
 *
 * @Date:2020/8/26 10:10
 * @Author:何磊
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private StudentService studentService;

    @Resource
    private TearcherService tearcherService;

    @Resource
    private AuthorityMapper authorityMapper;

    @Override
    public User login(User user) {

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(user.getUsername())){
            criteria.andUsernameEqualTo(user.getUsername());
        }
        if (StringUtils.isNotBlank(user.getUserpwd())){
            criteria.andUserpwdEqualTo(user.getUserpwd());
        }
        if (StringUtils.isNotBlank(String.valueOf(user.getUsertype()))){
            criteria.andUsertypeEqualTo(user.getUsertype());
        }
        List<User> users = userMapper.selectByExample(example);
        System.out.println(users + "========================");
        //如果查询到数据，则登录成功，如果没有数据则登录失败
        if (users.size() == 0){
            return null;
        }
        return users.get(0);
    }

    /**
     * 查询所有用户
     * @return
     */
    @Override
    public List<User> findUsers() {
        UserExample example = new UserExample();
        example.setOrderByClause("date desc");
        List<User> userList = userMapper.selectByExample(example);
        return userList;
    }

    @Override
    public List<User> findAllUser() {
        List<User> userList = userMapper.selectQuery();
        return userList;
    }

    @Override
    public int modifyStatus(String uid,String status) {
        User user = new User();
        user.setStatus(status);
        String[] uidList = uid.split(",");
        int result = 0;
        for (String str : uidList) {
            user.setUid(Long.valueOf(str));
            result += userMapper.updateByPrimaryKeySelective(user);
        }
        if (result == uidList.length ){
            return 1;
        }
        return -1;
    }

    /**
     * 修改用户的登录密码
     * @param user 用户的uid，username，userpwd
     * @return
     */
    @Override
    public int modifyPassword(User user) {
        int result = userMapper.updateByPrimaryKeySelective(user);
        return result;
    }

    @Override
    public int addUser(User user) {
        int result = userMapper.insert(user);

        //查询那你这个uid
        Long uid = userMapper.selectUserGetUid(user.getUsername());
        //新增权限表内容
        Authority authority = new Authority();
        authority.setUid(uid);

        if ("0".equals(user.getUsertype())){
            authority.setLevel("3");
        }else if ("1".equals(user.getUsertype())){
            authority.setLevel("1");
        }else {
            authority.setLevel("3");
        }
        authorityMapper.insert(authority);

        return result;
    }

    @Override
    public boolean findUserByName(String username,String usertype) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andUsertypeEqualTo(usertype);
        List<User> userList = userMapper.selectByExample(example);

        //true就是没数据，就是可用
        //false就是有数据，就是不可用
        return userList.size() == 0;
    }

    @Override
    public int modify(User user) {
        return  userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Long findUserGetUid(String username) {
        return userMapper.selectUserGetUid(username);
    }
    /**
     * 批量删除用户
     * @param str_uids
     * @return
     */
    @Override
    public Msg removeBatch(String[] str_uids) {
        return null;
    }
    /**
     * 删除单个用户
     * @param uids
     * @return
     */
    @Override
    public Msg remove(String uids) {
        //1、查询这个uid查询到这个用户，看看用户的用户类型
        int result = -1;
        User user = userMapper.selectByPrimaryKey(Long.valueOf(uids));

        //删除对应权限表
        authorityMapper.deleteByPrimaryKey(Long.valueOf(uids));

        if ("0".equals(user.getUsertype())){//删除管理员
            result = userMapper.deleteByPrimaryKey(Long.valueOf(uids));
        }else if ("1".equals(user.getUsertype())){//删除学生
            //删除的学生的id，查询学生表中uid为uids的那个学生删除他
            result = studentService.deleteByUid(uids);
        }else {//删除教师
            //删除的教师uid, 查询教师表中uid为uids的那个教师，删除他
            result = tearcherService.deleteByUid(uids);
        }
        if (result == -2){
            return  Msg.fail("删除失败，用户编号为"+uids+"的教师是班主任，请先更换班级班主任！");
        }else if (result == -1){
            return Msg.fail("删除失败，请联系管理员");
        }
        return Msg.success("删除成功",null,null);
    }

    @Override
    public List<User> fuzzyQuery(User user) {

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        if (user.getUsername()!=null && user.getUsername()!="" ){
            criteria.andUsernameLike("%"+user.getUsername()+"%");
            return  userMapper.selectByExample(example);
        }

        if (user.getStatus()!=null &&user.getStatus()!=""){
            if (user.getUsertype()!=null && user.getUsertype()!=""){
                criteria.andStatusEqualTo(user.getStatus());
                criteria.andUsertypeEqualTo(user.getUsertype());
                return userMapper.selectByExample(example);
            }
            criteria.andStatusEqualTo(user.getStatus());
            return userMapper.selectByExample(example);
        }
        if (user.getUsertype()!=null && user.getUsertype()!=""){
            criteria.andUsertypeEqualTo(user.getUsertype());
            return userMapper.selectByExample(example);
        }
        return null;
    }
}
