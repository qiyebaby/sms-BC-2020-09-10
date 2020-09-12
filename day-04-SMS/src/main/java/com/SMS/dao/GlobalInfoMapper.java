package com.SMS.dao;

import com.SMS.domain.GlobalInfo;
import com.SMS.domain.GlobalInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GlobalInfoMapper {
    long countByExample(GlobalInfoExample example);

    int deleteByExample(GlobalInfoExample example);

    int deleteByPrimaryKey(String info);

    int insert(GlobalInfo record);

    int insertSelective(GlobalInfo record);

    List<GlobalInfo> selectByExample(GlobalInfoExample example);

    GlobalInfo selectByPrimaryKey(String info);

    int updateByExampleSelective(@Param("record") GlobalInfo record, @Param("example") GlobalInfoExample example);

    int updateByExample(@Param("record") GlobalInfo record, @Param("example") GlobalInfoExample example);

    int updateByPrimaryKeySelective(GlobalInfo record);

    int updateByPrimaryKey(GlobalInfo record);
}