package com.SMS.service.Impl;

import com.SMS.dao.AuthorityMapper;
import com.SMS.dao.RoomMapper;
import com.SMS.dao.StudentMapper;
import com.SMS.dao.UserMapper;
import com.SMS.domain.*;
import com.SMS.service.StudentService;
import com.SMS.service.UserService;
import com.SMS.utils.AcademicBuild;
import com.SMS.utils.Const;
import com.SMS.utils.DateUtil;
import com.SMS.utils.Msg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: TODO //
 *
 * @Date:2020/8/30 10:12
 * @Author:何磊
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private AcademicBuild academicBuild;

    @Resource
    private RoomMapper roomMapper;


    @Resource
    private AuthorityMapper authorityMapper;

    @Override
    public List<Student> findAllClass() {
        List<Student> studentList = studentMapper.selectQuery(null);
        return studentList;
    }

    @Override
    public List<Student> fuzzyQuery(Student student) {
        List<Student> studentList = studentMapper.selectQuery(student);
        return studentList;
    }

    @Override
    public List<Student> selectByCno(Integer cno) {
        StudentExample example = new StudentExample();
        StudentExample.Criteria criteria = example.createCriteria();
        criteria.andCnoEqualTo(cno);
        List<Student> studentList = studentMapper.selectByExample(example);
        return studentList;
    }

    @Override
    public Student selectBySid(Integer no) {
        Student student = studentMapper.selectByPrimaryKey(no);
        return student;
    }

    @Override
    public Msg findStudentByName(String sno) {

        Student student = studentMapper.selectBySno(sno);

        //还需要判断这个学号作为登录账号是否可以
        //true就是没数据，就是可用
        //false就是有数据，就是不可用
        boolean userByName = userService.findUserByName(sno, "1");
        if (!userByName){
            return Msg.fail("该学号信息已经存在");
        }

        if (student == null){
            return Msg.success("该学号信息可用",null,null);
        }
        return Msg.fail("该学号信息已经存在");

    }


    @Transactional(
            propagation = Propagation.REQUIRED//默认的传播就是required
            ,isolation = Isolation.DEFAULT
            ,rollbackFor = {
                    Exception.class,
                    NullPointerException.class
            }
    )
    @Override
    public Msg addStudent(Student student) {
        //1、再次验证寝室楼是否可用
        Msg res = academicBuild.studentRoomIsAvailable(student.getNum(), student.getFloor(), student.getRoom());
        if (res.getCode() == "1"){
            return  Msg.fail("当前寝室已经满员，请更换");
        }

        //2、验证是否可用
        Msg studentByName = findStudentByName(student.getSno());
        if (studentByName.getCode() == "1"){
            return Msg.fail("该学号信息已经存在");
        }

        //3、插入登录信息，插入学生信息
        //拼装user
        User user = new User(null,student.getUsername(),student.getUserpwd(),
                DateUtil.getTime(Const.TIME_FORMAT_TWO),"1","1");
        user.setUsername(String.valueOf(student.getSno()));
        int i = userService.addUser(user);
        if (i == 1){//新增用户成功




            //查询主键uid，作为学生表外键
            Long uid =userService.findUserGetUid(user.getUsername());
            student.setUid(uid);
            student.setDorm(AcademicBuild.toAddress(student.getNum(), student.getFloor(), student.getRoom()));

            //插入学生
            int result = studentMapper.insertSelective(student);
            if (result == 1){

                //1、学生信息已经加入
                //2、加入寝室信息,判断这个寝室号是否已经在寝室表中存在
                //3、如果存在直接给people+1，否则插入这个寝室号，people为1
                RoomExample example = new RoomExample();
                RoomExample.Criteria criteria = example.createCriteria();
                criteria.andDormEqualTo(student.getDorm());
                List<Room> roomList = roomMapper.selectByExample(example);
                Room room = null;
                if (roomList.size() == 0){//寝室不存在
                    room = new Room();
                    room.setDorm(student.getDorm());
                    room.setPeople(1);
                    roomMapper.insertSelective(room);
                }else {//寝室已经存在
                    room = roomList.get(0);
                    room.setPeople(room.getPeople()+1);
                    roomMapper.updateByPrimaryKey(room);
                }


                return Msg.success("新增学生成功",null,null);
            }else {
                return Msg.fail("新增学生失败，请联系管理员");
            }

        }else {
            return Msg.fail("新增学生登录信息失败，请联系管理员");
        }
    }


    /**
     * 通过班级编号查询学生信息，目的是判断这个班级是否有学生，没有学到就可以删除
     * @param str_cno
     * @return
     */
    @Override
    public List<Student> selectStudentCno(String str_cno) {

        StudentExample example = new StudentExample();
        StudentExample.Criteria criteria = example.createCriteria();
        criteria.andCnoEqualTo(Integer.parseInt(str_cno));

        List<Student> studentList = studentMapper.selectByExample(example);
        return studentList;
    }

    @Override
    public int removeBatch(List<Integer> sid_list, List<Long> uid_list) {
        int i= 0;
        StudentExample example1 = new StudentExample();
        StudentExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andSidIn(sid_list);

        AuthorityExample example2 = new AuthorityExample();
        AuthorityExample.Criteria criteria2 = example2.createCriteria();
        criteria2.andUidIn(uid_list);

        UserExample example3 = new UserExample();
        UserExample.Criteria criteria3 = example3.createCriteria();
        criteria3.andUidIn(uid_list);

        i += studentMapper.deleteByExample(example1);
        i += authorityMapper.deleteByExample(example2);
        i += userMapper.deleteByExample(example3);
        return i;
    }

    @Override
    public int remove(String sids, String uids) {
        int i= 0;
        i += studentMapper.deleteByPrimaryKey(Integer.parseInt(sids));
        i += authorityMapper.deleteByPrimaryKey(Long.valueOf(uids));
        i += userMapper.deleteByPrimaryKey(Integer.toUnsignedLong(Integer.parseInt(uids)));
        return i;
    }


    //判断这个学生所在寝室是不是只有他一个人，是的话先删除寝室，不是的话寝室人数-1
    //封装一个方法，传入sid
    @Override
    public void isOnlyPeople(String sids) {

        Student student = studentMapper.selectByPrimaryKey(Integer.parseInt(sids));
        //1、查询这个学生所在寝室是否只有一个人
        RoomExample example = new RoomExample();
        RoomExample.Criteria criteria = example.createCriteria();
        criteria.andDormEqualTo(student.getDorm());
        List<Room> roomList = roomMapper.selectByExample(example);
        Room room = roomList.get(0);
        if (room.getPeople() == 1){//只有一个人，删除寝室信息
            roomMapper.deleteByPrimaryKey(room.getRid());
        }else {//不止一个人，人数减1，寝室依旧存在
            room.setPeople(room.getPeople()-1);
            roomMapper.updateByPrimaryKeySelective(room);
        }

    }

    //删除某个用户---就是某个学生
    @Override
    public Integer deleteByUid(String uids) {
        StudentExample example = new StudentExample();
        StudentExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(Long.valueOf(uids));
        List<Student> studentList = studentMapper.selectByExample(example);
        if (studentList.size() == 0 ){
            //这个登录账号实际上没有学生信息
            //可以直接删除
            return userMapper.deleteByPrimaryKey(Long.valueOf(uids));
        }else {
            //删除这个学生
            Student student = studentList.get(0);
            return  remove(String.valueOf(student.getSid()), uids);
        }
    }

    @Override
    public Msg modify(Student student) {
        int i = studentMapper.updateByPrimaryKeySelective(student);
        if (i == 1){
            return Msg.success("更新成功",null,null);

        }else {
            return  Msg.fail("更新失败");
        }
    }
}
