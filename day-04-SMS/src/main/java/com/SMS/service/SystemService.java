package com.SMS.service;

import com.SMS.domain.User;
import com.SMS.utils.Msg;
/**
 * Description: TODO //
 *
 * @Date:2020/9/7 13:46
 * @Author:何磊
 */
public interface SystemService {
    Msg administrator(User user);

    Msg student(User user);

    Msg tearcher(User user);

    int modify(Long uid, String new_password);

}
