package com.SMS.service.Impl;

import com.SMS.dao.RoomMapper;
import com.SMS.dao.StudentMapper;
import com.SMS.domain.Room;
import com.SMS.domain.RoomExample;
import com.SMS.domain.Student;
import com.SMS.domain.StudentExample;
import com.SMS.service.RoomService;
import com.SMS.utils.AcademicBuild;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: TODO //
 *
 * @Date:2020/9/2 11:19
 * @Author:何磊
 */
@Service
public class RoomServiceImpl implements RoomService {

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private StudentMapper studentMapper;

    @Override
    public List<Room> queryAll() {
        List<Room> roomList = roomMapper.selectByExample(null);
        return roomList;
    }

    @Override
    public List<Student> query(String dorm) {
        List<Student> studentList = studentMapper.selectByDorm(dorm);
        return studentList;
    }

    @Override
    public int submitGrade(Room room) {
        return  roomMapper.updateSubmitGrade(room);
    }

    @Override
    public int addRemark(Room room) {
        return roomMapper.updateByPrimaryKeySelective(room);
    }

    @Override
    public List<Room> fuzzyQuery(Room room) {

        RoomExample example = new RoomExample();
        RoomExample.Criteria criteria = example.createCriteria();

        if (room.getNum()!=null){
            if (room.getFloor()!=null){
                if (room.getRoom()!=null){
                    String dorm = AcademicBuild.toAddress(room.getNum(), room.getFloor(), room.getRoom());
                    criteria.andDormLike("%"+dorm+"%");
                    return roomMapper.selectByExample(example);
                }
                criteria.andDormLike("%"+room.getNum()+"#-"+room.getFloor()+"%");
                return roomMapper.selectByExample(example);
            }
            criteria.andDormLike("%"+room.getNum()+"#%");
            return roomMapper.selectByExample(example);
        }
        if (room.getFloor()!=null){
            if (room.getRoom()!=null){
                String dorm = AcademicBuild.toAddress(room.getNum(), room.getFloor(), room.getRoom());
                criteria.andDormLike("%"+dorm+"%");
                return roomMapper.selectByExample(example);
            }
            criteria.andDormLike("%"+room.getNum()+"#-"+room.getFloor()+"%");
            return roomMapper.selectByExample(example);
        }
        return null;
    }
}
