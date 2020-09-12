package com.SMS.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: TODO //
 *
 * @Date:2020/8/27 11:18
 * @Author:何磊
 */
@Component
public class Msg {
    private String code;
    private String info;
    private List<Object> data;
    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    private Map<String,Object> extend = new HashMap<>();


    public static Msg success(String info, List dataList,Long count){
        Msg msg = new Msg();
        msg.setCode("0");
        msg.setInfo(info);
        msg.setData(dataList);
        msg.setCount(count);
        return msg;
    }
    public static Msg fail(String info){
        Msg msg = new Msg();
        msg.setCode("1");
        msg.setInfo(info);
        return msg;
    }
    public Msg add(String key,Object value){
        this.getExtend().put(key,value);
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
