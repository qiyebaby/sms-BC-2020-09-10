package com.SMS.controller;

import com.SMS.domain.Room;
import com.SMS.domain.Student;
import com.SMS.service.RoomService;
import com.SMS.utils.AcademicBuild;
import com.SMS.utils.Msg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Description: TODO //
 *
 * @Date:2020/9/2 11:13
 * @Author:何磊
 */
@Controller
@RequestMapping("/room")
public class RoomController {

    @Resource
    private RoomService roomService;

    @Resource
    private AcademicBuild academicBuild;

    /**
     * 查询学生寝室信息并展示
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryAll")
    public Msg queryAll(Integer page,Integer limit,Room room){
        PageHelper.startPage(page,limit);
        List<Room> roomList = null;

        if (room.getNum()!=null||room.getFloor()!=null||room.getRoom()!= null){
            roomList = roomService.fuzzyQuery(room);
        }else {
            roomList = roomService.queryAll();
        }
        PageInfo pageInfo = new PageInfo(roomList);
        return Msg.success("处理成功",roomList,pageInfo.getTotal());
    }

    /**
     * query
     * 1、查询这个寝室的所有成员
     */
    @ResponseBody
    @RequestMapping("/query")
    public Msg query(Integer num,Integer floor,Integer room){
        String dorm = AcademicBuild.toAddress(num, floor, room);
        List<Student> roomList = roomService.query(dorm);
        return Msg.success("处理成功",roomList,Long.valueOf(roomList.size()));
    }

    /**
     * 验证房间号是否可用
     */
    @RequestMapping("/isAvailable")
    @ResponseBody
    public Msg isAvailable(Integer num,Integer floor,Integer room){
        return academicBuild.studentRoomIsAvailable(num,floor,room);
    }

    /**
     * submit
     * 提交寝室本周得分
     */
    @RequestMapping("/submit")
    @ResponseBody
    public Msg modify(Integer grade,Integer num,Integer floor,Integer room){

        String dorm = AcademicBuild.toAddress(num, floor, room);
        Room roomObj = new Room();
        roomObj.setDorm(dorm);
        roomObj.setGrade(grade);
        int result = roomService.submitGrade(roomObj);
        if (result != 1){
            return Msg.fail("提交分数失败");
        }else {
            return Msg.success("提交分数成功",null,null);
        }
    }

    /**
     * save
     * 保存备注信息
     */
    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Msg addRoom(Room room, HttpSession session, HttpServletRequest request){

        //添加备注
        int result = roomService.addRemark(room);
        if (result == 1){
            return Msg.success("添加备注成功",null,null);
        }else
        {
            return Msg.fail("添加备注失败");
        }


    }

}
