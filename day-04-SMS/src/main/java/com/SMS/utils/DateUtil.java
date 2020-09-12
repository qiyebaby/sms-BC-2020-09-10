package com.SMS.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Description: TODO // 日期工具类
 *
 * @Date:2020/9/1 10:34
 * @Author:何磊
 */
public class DateUtil {

    /**
     * 根据格式获取日期时间
     * @param format 格式
     * @return  日期
     */
    public static String getTime(String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

}
