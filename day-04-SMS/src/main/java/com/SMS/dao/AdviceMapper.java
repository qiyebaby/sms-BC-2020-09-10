package com.SMS.dao;

import com.SMS.domain.Advice;
import com.SMS.domain.AdviceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdviceMapper {
    long countByExample(AdviceExample example);

    int deleteByExample(AdviceExample example);

    int deleteByPrimaryKey(Integer aid);

    int insert(Advice record);

    int insertSelective(Advice record);

    List<Advice> selectByExample(AdviceExample example);

    Advice selectByPrimaryKey(Integer aid);

    int updateByExampleSelective(@Param("record") Advice record, @Param("example") AdviceExample example);

    int updateByExample(@Param("record") Advice record, @Param("example") AdviceExample example);

    int updateByPrimaryKeySelective(Advice record);

    int updateByPrimaryKey(Advice record);
}