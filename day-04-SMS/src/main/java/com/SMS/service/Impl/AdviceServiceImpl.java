package com.SMS.service.Impl;

import com.SMS.dao.AdviceMapper;
import com.SMS.domain.Advice;
import com.SMS.domain.AdviceExample;
import com.SMS.service.AdviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: TODO //
 *
 * @Date:2020/9/8 16:25
 * @Author:何磊
 */
@Service
public class AdviceServiceImpl implements AdviceService {
    @Resource
    private AdviceMapper adviceMapper;
    @Override
    public int addSingle(Advice advice) {
        int result = adviceMapper.insert(advice);
        return result;
    }
    @Override
    public List<Advice> findAdviceByFive() {
        AdviceExample example  = new AdviceExample();
        AdviceExample.Criteria criteria = example.createCriteria();
        criteria.andLevelEqualTo(5);
        List<Advice> advice = adviceMapper.selectByExample(example);
        return advice;
    }

    @Override
    public List<Advice> findAdviceByThree() {
        AdviceExample example  = new AdviceExample();
        AdviceExample.Criteria criteria = example.createCriteria();
        criteria.andLevelEqualTo(3);
        List<Advice> advice = adviceMapper.selectByExample(example);
        return advice;
    }
    @Override
    public List<Advice> findAdviceByFour() {
        AdviceExample example  = new AdviceExample();
        AdviceExample.Criteria criteria = example.createCriteria();
        criteria.andLevelEqualTo(4);
        List<Advice> advice = adviceMapper.selectByExample(example);
        return advice;
    }

    @Override
    public List<Advice> findAdviceByTwo(Long uid) {
        AdviceExample example  = new AdviceExample();
        AdviceExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        criteria.andLevelEqualTo(2);
        List<Advice> advice = adviceMapper.selectByExample(example);
        return advice;
    }

    @Override
    public List<Advice> findAdviceByOne(Long uid) {
        AdviceExample example  = new AdviceExample();
        AdviceExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        criteria.andLevelEqualTo(1);
        List<Advice> advice = adviceMapper.selectByExample(example);
        return advice;
    }

    @Override
    public int remove(Integer aid) {
        int i = adviceMapper.deleteByPrimaryKey(aid);
        return i;
    }
}
