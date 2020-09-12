package com.SMS.utils;

import com.SMS.dao.MyClassMapper;
import com.SMS.dao.RoomMapper;
import com.SMS.domain.MyClass;
import com.SMS.domain.MyClassExample;
import com.SMS.domain.Room;
import com.SMS.domain.RoomExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description: TODO // 教学楼
 *
 * @Date:2020/8/29 11:46
 * @Author:何磊
 */
@Component
public class AcademicBuild {

    @Autowired
    private MyClassMapper classMapper;

    @Autowired
    private RoomMapper roomMapper;

    /**
     * 将对应数字转换为楼层具体名
     * @param num   楼号
     * @param floor 楼层
     * @param room  房间号
     * @return  8#-101 类似
     */
    public static  String toAddress(Integer num,Integer floor,Integer room){
        return num+"#-"+floor+"0"+room;
    }

    /**
     * 判断当前教学楼地址是否可用
     * @param myClass
     * @return  真就是可用，false就是不可用
     */
    public  boolean builderRoomIsAvailable(MyClass myClass){
        String address = AcademicBuild.toAddress(myClass.getNum(),myClass.getFloor(),myClass.getRoom());
        MyClassExample example = new MyClassExample();
        MyClassExample.Criteria criteria = example.createCriteria();
        criteria.andAddressEqualTo(address);
        List<MyClass> classList = classMapper.selectByExample(example);
        if (classList.size() == 0){
            return true;
        }
        return false;
    }

    /**
     * 判断当前寝室楼是否可用。
     * @param num
     * @param floor
     * @param room
     * @return
     */
    public Msg studentRoomIsAvailable(Integer num,Integer floor,Integer room) {

        String address = AcademicBuild.toAddress(num,floor,room);

        RoomExample example = new RoomExample();
        RoomExample.Criteria criteria = example.createCriteria();
        criteria.andDormEqualTo(address);

        List<Room> roomList = roomMapper.selectByExample(example);

        if (roomList.size() == 0){
            return  Msg.success("可以使用",null,null);
        }
        Integer people = roomList.get(0).getPeople();
        if (people == 4){
            return Msg.fail("当前寝室已经满员，请更换");
        }

        return  Msg.success("可以使用",null,null);
    }
}
