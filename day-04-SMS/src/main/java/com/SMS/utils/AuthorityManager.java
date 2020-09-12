package com.SMS.utils;

import com.SMS.dao.AuthorityMapper;
import com.SMS.domain.Authority;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Description: TODO // 权限管理
 *
 * @Date:2020/9/5 16:59
 * @Author:何磊
 */
@Component
public class AuthorityManager {

    @Resource
    private AuthorityMapper authorityMapper;

    /**
     * 权限等级
     * 1、一级只允许查看   --->最低级权限    -->学生
     * 2、二级允许新增     --->二级权限      -->教师新增本班学生
     * 3、三级允许修改     --->三级权限      -->教师修改本班学生     有个三级是为了给普通管理员过度
     * 4、四级允许删除     --->最高权限
     */
    private String level;
    /*
            分析：
            1、使用前置通知进行权限控制【前置通知无法返回消息】
              【     考虑使用环绕通知，返回Msg---json数据   】

            2、分别针对删除，修改，添加

            3、系统管理员可以设置权限，其他人无法进行权限设置

            4、有一张表，username和usertype和权限等级之间的关系

            5、 学生等级就是1
                普通管理员就是2，不允许修改【默认的，目的是可以修改，或者修改教师的等级也为这个】
                教师等级就是3
                系统管理员就是4

            6、 authority权限表
                uid(主键共享)  usertype     level
                1               0           4       系统管理员
                2               1           1       学生

            新增教师的时候插入这个信息：如果是教师权限设置为3

            新增学生的时候插入这个信息：如果是学生权限设置为1

            新增管理员的时候插入这个信息：如果是管理员权限设置为2

            用户页面新增一个按钮设置权限等级【只有权限等级为4的人可以设置】

     */


    public void setLevel(String level){
        this.level = level;
    }
    public String getLevel(){
        return this.level;
    }

    /**
     * 设置权限，需要uid和权限等级
     */
    public void setUserAuthority(Authority authority){
        authorityMapper.updateByPrimaryKey(authority);
    }
    /**
     * 删除权限表对应信息，如果这个user被删除，权限表一并删除
     */
    public void delUserAuthority(Long uid){
        authorityMapper.deleteByPrimaryKey(uid);
    }






}

