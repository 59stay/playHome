/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: MyIplicationListener
 * Author:   jyb
 * Date:     2019/3/29 13:34
 * Description: ip监听器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.jyb.Listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @描述〈ip监听器〉
 * @作者 jyb
 * @创建时间 2019/3/29
 */
@WebListener
public class IplicationListener implements ServletContextListener {
    private Logger logger =  LoggerFactory.getLogger(IplicationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("开始初始化监听信息");
        System.err.println("IplicationListener初始化成功");
        ServletContext context = sce.getServletContext();
        // IP存储器
        Map<String, Long[]> ipMap = new HashMap<String, Long[]>();
        context.setAttribute("ipMap", ipMap);
        // 限制IP存储器：存储被限制的IP信息
        Map<String, Long> limitedIpMap = new HashMap<String, Long>();
        context.setAttribute("limitedIpMap", limitedIpMap);
        logger.info("ipmap："+ipMap.toString()+";limitedIpMap:"+limitedIpMap.toString()+"初始化成功。。。。。");
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("初始化监听结束");
    }
}