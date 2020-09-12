package com.SMS.service;

import com.SMS.domain.Advice;
import java.util.List;

/**
 * Description: TODO //
 *
 * @Date:2020/9/8 16:24
 * @Author:何磊
 */
public interface AdviceService {

    public int addSingle(Advice advice);

    List<Advice> findAdviceByFive();


    List<Advice> findAdviceByThree();
    List<Advice> findAdviceByFour();


    List<Advice> findAdviceByTwo(Long uid);

    List<Advice> findAdviceByOne(Long uid);

    int remove(Integer aid);

}
