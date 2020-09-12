package com.SMS.service;

import com.SMS.domain.Student;
import com.SMS.utils.Msg;

import java.util.List;

/**
 * Description: TODO //
 *
 * @Date:2020/8/30 10:11
 * @Author:何磊
 */
public interface StudentService {
    List<Student> findAllClass();

    Msg findStudentByName(String realname);

    Msg addStudent(Student student);

    List<Student> selectStudentCno(String str_cno);

    int removeBatch(List<Integer> sid_list, List<Long> uid_list);

    int remove(String sids, String uids);

    void isOnlyPeople(String sids);

    Integer deleteByUid(String uids);

    Msg modify(Student student);

    List<Student> fuzzyQuery(Student student);

    List<Student> selectByCno(Integer cno);

    Student selectBySid(Integer no);

}
