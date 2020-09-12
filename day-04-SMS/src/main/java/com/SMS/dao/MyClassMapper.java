package com.SMS.dao;

import com.SMS.domain.MyClass;
import com.SMS.domain.MyClassExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MyClassMapper {
    long countByExample(MyClassExample example);

    int deleteByExample(MyClassExample example);

    int deleteByPrimaryKey(Integer cno);

    int insert(MyClass record);

    int insertSelective(MyClass record);

    List<MyClass> selectByExample(MyClassExample example);

    MyClass selectByPrimaryKey(Integer cno);

    //自定义查询，所有班级信息
    List<MyClass> selectAllClass();

    int updateByExampleSelective(@Param("record") MyClass record, @Param("example") MyClassExample example);

    int updateByExample(@Param("record") MyClass record, @Param("example") MyClassExample example);

    int updateByPrimaryKeySelective(MyClass record);

    int updateByPrimaryKey(MyClass record);

    int update(MyClass myClass);

    MyClass selectByHeadmaster(int parseInt);

}