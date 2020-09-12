package com.SMS.dao;

import com.SMS.domain.Tearcher;
import com.SMS.domain.TearcherExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TearcherMapper {
    long countByExample(TearcherExample example);

    int deleteByExample(TearcherExample example);

    int deleteByPrimaryKey(Integer tid);

    int insert(Tearcher record);

    int insertSelective(Tearcher record);

    List<Tearcher> selectByExample(TearcherExample example);

    Tearcher selectByPrimaryKey(Integer tid);

    /*
        定义查询方法，查询所有不是班主任的教师
     */
    List<Tearcher> selectAllNotHeadmaster();
    /*
        分页查询教师+教师的登录账号
     */
    List<Tearcher> selectQuery();

    int updateByExampleSelective(@Param("record") Tearcher record, @Param("example") TearcherExample example);

    int updateByExample(@Param("record") Tearcher record, @Param("example") TearcherExample example);

    int updateByPrimaryKeySelective(Tearcher record);

    int updateByPrimaryKey(Tearcher record);

    Tearcher selectTearcherByName(String realname);


    List<Tearcher> selectByUid(String uids);

    Tearcher selectByCno(Integer cno);

}