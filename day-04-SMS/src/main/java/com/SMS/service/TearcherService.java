package com.SMS.service;

import com.SMS.domain.Tearcher;
import com.SMS.utils.Msg;

import java.util.List;

/**
 * Description: TODO //
 *
 * @Date:2020/8/29 16:12
 * @Author:何磊
 */
public interface TearcherService {
    List<Tearcher> findAllDept();

    List<Tearcher> findAllTearcher();


    Msg modify(Tearcher tearcher);


    Msg findTearcherByName(String realname);

    Msg addTearcher(Tearcher tearcher);

    int removeBatch(List<Integer> tid_list,List<Long> uid_list);

    int remove(String tids,String uids);

    int deleteByUid(String uids);

    List<Tearcher> fuzzyQuery(String realname);

    Tearcher selectByCno(Integer cno);

    Tearcher selectBytid(Integer no);

    Tearcher selectByUid(Long uid);
}
