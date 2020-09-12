package com.SMS.utils;

import com.SMS.dao.GlobalInfoMapper;
import com.SMS.domain.GlobalInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Description: TODO // 统计浏览总数
 *
 * @Date:2020/8/31 9:19
 * @Author:何磊
 */
@Component
public class StatisticsInfo {

    @Resource
    private GlobalInfoMapper infoMapper;

    private String infoStatistics;

    /**
     * 从数据库中读取浏览总数【也就是整个系统总得登录次数】
     * @return  浏览总数
     */
    public String getInfoStatistics(HttpServletRequest request) {
        GlobalInfo globalInfo = infoMapper.selectByPrimaryKey("global_infoStatistics");
        return globalInfo.getValue();
    }

    /**
     *  将浏览次数写入数据库
     * @param infoStatistice    新的浏览次数
     */
    public void setInfoStatistics(String infoStatistics) {
        GlobalInfo globalInfo = new GlobalInfo();
        globalInfo.setInfo("global_infoStatistics");
        globalInfo.setValue(infoStatistics);
        infoMapper.updateByPrimaryKey(globalInfo);
    }

    /**
     * 定义一个登录以后浏览次数+1的操作
     */

    public void statisticsAddOne(HttpServletRequest request){
        ServletContext servletContext = request.getServletContext();
        String info = (String) servletContext.getAttribute("infoStatistics");
        System.out.println(info);
        info = String.valueOf(Integer.parseInt(info) + 1);
//        写入数据库
        setInfoStatistics(info);
    }

    /**
     * 读取浏览总数，存入ServletContext
     */
    public void getStatisticsToServletContext(HttpServletRequest request){
        GlobalInfo globalInfo = infoMapper.selectByPrimaryKey("global_infoStatistics");
        //放行之前，从数据库中读取浏览总数放入ServletContext
        ServletContext servletContext = request.getServletContext();
        servletContext.setAttribute("infoStatistics",globalInfo.getValue());
    }

}
