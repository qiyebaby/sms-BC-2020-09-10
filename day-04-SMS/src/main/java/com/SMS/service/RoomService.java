package com.SMS.service;

import com.SMS.domain.Room;
import com.SMS.domain.Student;

import java.util.List;

/**
 * Description: TODO //
 *
 * @Date:2020/9/2 11:18
 * @Author:何磊
 */
public interface RoomService {


    List<Room> queryAll();


    List<Student> query(String dorm);

    int submitGrade(Room room);

    int addRemark(Room room);

    List<Room> fuzzyQuery(Room room);

}
