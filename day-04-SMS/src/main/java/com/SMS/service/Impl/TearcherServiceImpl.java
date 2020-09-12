package com.SMS.service.Impl;

import com.SMS.dao.AuthorityMapper;
import com.SMS.dao.MyClassMapper;
import com.SMS.dao.TearcherMapper;
import com.SMS.dao.UserMapper;
import com.SMS.domain.*;
import com.SMS.service.ClassService;
import com.SMS.service.TearcherService;
import com.SMS.service.UserService;
import com.SMS.utils.Const;
import com.SMS.utils.DateUtil;
import com.SMS.utils.Msg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: TODO //
 *
 * @Date:2020/8/29 16:14
 * @Author:何磊
 */
@Service
public class TearcherServiceImpl implements TearcherService {
    @Resource
    private TearcherMapper tearcherMapper;

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ClassService classService;

    @Resource
    private AuthorityMapper authorityMapper;


    @Override
    public List<Tearcher> findAllDept() {
        //需要自定义查询方法
        List<Tearcher> tearcherList = tearcherMapper.selectAllNotHeadmaster();

        return tearcherList;
    }

    @Override
    public List<Tearcher> findAllTearcher() {
        List<Tearcher> tearcherList = tearcherMapper.selectQuery();
        return tearcherList;
    }

    @Override
    public Msg modify(Tearcher tearcher) {
        System.out.println(tearcher.toString());

        int result = tearcherMapper.updateByPrimaryKeySelective(tearcher);
        if (result == 1){
            //成功
            return Msg.success("处理成功",null,null);
        }else {
            //失败
            return Msg.fail("更新教师信息失败");
        }
    }

    @Override
    public Msg findTearcherByName(String realname) {

        Tearcher tearcher = tearcherMapper.selectTearcherByName(realname);
        if (tearcher == null){
            return Msg.success("该教师信息可用",null,null);
        }
        return Msg.fail("该教师信息已经存在");
    }

    /**
     * 处理教师新增的业务逻辑
     *
     * @param tearcher
     * @return
     */
    @Override
    public Msg addTearcher(Tearcher tearcher) {

        //1、教师是否存在的验证
        //   如果姓名一致，手机号也一致则认为是重复教师
        Msg tearcherByName = findTearcherByName(tearcher.getRealname());
        //如果是错误信息就返回
        if ("1".equals(tearcherByName.getCode())){
            return tearcherByName;
        }

        //2、教师信息可用
        //true就是没数据，就是可用
        //false就是有数据，就是不可用
        boolean userByName = userService.findUserByName(tearcher.getUsername(), "2");
        if (!userByName){
            return Msg.fail("用户已经存在，请更换登录用户名");
        }
        //3、程序走到这，说明这个教师信息完全可用
        //   新插入父表在插入子表，教师表引用了登录表，所以先插入登录信息，在插入教师信息
        //   判断登录名是否可用

        //拼装user
        User user = new User(null,tearcher.getUsername(),tearcher.getUserpwd(),
                DateUtil.getTime(Const.TIME_FORMAT_TWO),"2","1");
        int i = userService.addUser(user);
        if (i == 1){//新增用户成功


//            获取uid
            Long uid =userService.findUserGetUid(user.getUsername());
            //完善教师信息-入职日期   - uid即登录账号
            tearcher.setEntrydate(DateUtil.getTime(Const.TIME_FORMAT_ONE));
            tearcher.setUid(uid);

            //新增教师信息
            int result = tearcherMapper.insertSelective(tearcher);
            if (result == 1){
                return Msg.success("新增成功",null,null);
            }else {
                return Msg.fail("新增教师失败，请联系管理员！");
            }
        }else {
            return Msg.fail("为该教师创建登录账号失败，请联系管理员！");
        }
    }

    /**
     * 批量删除
     * @param tid_list
     * @return
     */
    @Override
    public int removeBatch(List<Integer> tid_list,List<Long> uid_list) {
        int i= 0;
        TearcherExample example1 = new TearcherExample();
        TearcherExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andTidIn(tid_list);

        AuthorityExample example2 = new AuthorityExample();
        AuthorityExample.Criteria criteria2 = example2.createCriteria();
        criteria2.andUidIn(uid_list);

        UserExample example3 = new UserExample();
        UserExample.Criteria criteria3 = example3.createCriteria();
        criteria3.andUidIn(uid_list);

        i += tearcherMapper.deleteByExample(example1);

        i += authorityMapper.deleteByExample(example2);

        i += userMapper.deleteByExample(example3);
        return i;
    }
    /**
     * 单个删除
     * @param tids
     * @return
     */
    @Override
    public int remove(String tids,String uids) {
        int i= 0;
        i += tearcherMapper.deleteByPrimaryKey(Integer.parseInt(tids));
        i += authorityMapper.deleteByPrimaryKey(Long.valueOf(uids));
        i += userMapper.deleteByPrimaryKey(Long.valueOf(uids));
        return i;
    }

    @Override
    public int deleteByUid(String uids) {

        List<Tearcher> tearcherList = tearcherMapper.selectByUid(uids);
        if (tearcherList.size() == 0 ){
            //这个登录账号实际上没有教师信息
            //可以直接删除
            return userMapper.deleteByPrimaryKey(Long.valueOf(uids));
        }else {
            //删除这个教师
            Tearcher tearcher = tearcherList.get(0);
            //如果删除的这个教师是班主任，不允许删除
            MyClass myClass = classService.selectClassByHeadmaster(String.valueOf(tearcher.getTid()));
            if (myClass != null){//
                return -2;
            }
            return  remove(String.valueOf(tearcher.getTid()), uids);
        }
    }

    @Override
    public List<Tearcher> fuzzyQuery(String realname) {
        TearcherExample example = new TearcherExample();
        TearcherExample.Criteria criteria = example.createCriteria();
        criteria.andRealnameLike("%"+realname+"%");
        return  tearcherMapper.selectByExample(example);
    }

    @Override
    public Tearcher selectByCno(Integer cno) {
       Tearcher tearcher =  tearcherMapper.selectByCno(cno);
       return tearcher;
    }

    @Override
    public Tearcher selectBytid(Integer no) {
        Tearcher tearcher = tearcherMapper.selectByPrimaryKey(no);
        return tearcher;
    }

    @Override
    public Tearcher selectByUid(Long uid) {
        List<Tearcher> tearcherList = tearcherMapper.selectByUid(String.valueOf(uid));
        return tearcherList.get(0);
    }
}
