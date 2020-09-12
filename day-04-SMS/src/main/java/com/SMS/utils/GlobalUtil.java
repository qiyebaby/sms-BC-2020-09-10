package com.SMS.utils;

import com.SMS.domain.Student;
import com.SMS.domain.Tearcher;
import com.SMS.domain.User;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Description: TODO //
 *
 * @Date:2020/8/31 16:11
 * @Author:何磊
 */
public class GlobalUtil {

    /**
     * 关于能否查看密码的权限管理，只有管理员才有全查看所有人的登录密码
     * @param session   当前登录账户的session
     * @param list  返回的数据集合
     */
    public static void setUserpwdToNull(HttpSession session, List list){
        User user = (User) session.getAttribute("userInfo");
        if (user == null){
            return;
        }
        if ("1".equals(user.getUsertype()) ||"2".equals(user.getUsertype()) ){
            for (Object o : list) {
                if (o instanceof Tearcher){
                    Tearcher tearcher = (Tearcher)o;
                    tearcher.setUserpwd("******");
                }else if (o instanceof Student){
                    Student student = (Student) o;
                    student.setUserpwd("******");
                }else if (o instanceof User){
                    User userNew = (User) o;
                    userNew.setUserpwd("******");
                }
            }
        }
    }
}
