package com.SMS.service;

import com.SMS.domain.User;
import com.SMS.utils.Msg;

import java.util.List;
/**
 * Description: TODO //
 *
 * @Date:2020/8/26 10:09
 * @Author:何磊
 */
public interface UserService {

    User login(User user);

    List<User> findUsers();

    List<User> findAllUser();


    int modifyStatus(String uid,String status);

    int modifyPassword(User user);


    int addUser(User user);

    boolean findUserByName(String username,String usertype);

    int modify(User user);

    Long findUserGetUid(String username);

    Msg removeBatch(String[] str_uids);

    Msg remove(String uids);

    List<User> fuzzyQuery(User user);

}
