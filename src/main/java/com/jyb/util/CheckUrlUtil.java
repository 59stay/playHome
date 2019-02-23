package com.jyb.util;


import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 检查百度云分享链接是否有效
 * @author Administrator
 *
 */
public class CheckUrlUtil {
	 private static Logger log = LoggerFactory.getLogger(CheckUrlUtil.class);
	 
	 private static CloseableHttpClient httpClient=HttpClients.createDefault();
	/**
	 * 检查百度云链接是否有效
	 * @param link
	 * @return
	 */
	public static boolean checkBDY(String link)throws Exception{
		HttpGet httpget = new HttpGet(link); 
		httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0"); // 设置请求头消息User-Agent
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpget);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("=========================URL连接不可用！============================");
			return false;		
		}
		HttpEntity entity=response.getEntity(); // 获取返回实体  
		String result=EntityUtils.toString(entity, "utf-8");
		if(result.contains("请输入提取码")||result.contains("分享无限制")){
			log.info("=========================百度云盘URL连接可用！============================"); 
			return true;
		}else{
			log.info("=========================百度云盘URL连接不可用！============================"); 
			return false;			
		}
	}
	
	/**
	 * 功能：检测当前URL是否可连接或是否有效
	 * 描述：最多连接网络3 次, 如果3次都不成功，视为该地址不可用 
	 * @param urlString
	 * @param timeOutMillSeconds  //超时间
	 * @return
	 */
	public static boolean checkUrl(String urlString,int timeOutMillSeconds){
        long lo = System.currentTimeMillis();
        int counts = 0;  
        URL url; 
        boolean flag = false;
        while (counts < 3) {  
        	  try {  
                  url = new URL(urlString);  
                  URLConnection co =  url.openConnection();
                  co.setConnectTimeout(timeOutMillSeconds);
                  co.connect();
                  flag =  true;
                  log.info("=========================其他URL连接可用！============================"); 
                  break;  
             } catch (Exception e) { 
             	  counts++;  
                  url = null;  
                  log.info("=======================第"+counts+"次尝试连接============================"); 
                  log.info("=======================其他URL连接不可用！============================"); 
                  continue;  
             } 
        }
        log.info("=======================总耗时"+(System.currentTimeMillis()-lo)*0.001+"秒============================"); 
		return flag;
    }
	public static void main(String[] args) throws Exception {
		//boolean a = CheckUrlUtil.checkUrl("https://www.baidu.com/s?ie=utf-8&f=3&rsv_bp=1&tn=monline_3_dg&wd=java%E5%88%A4%E6%96%ADurl%E6%98%AF%E5%90%A6%E6%9C%89%E6%95%88&oq=java%25E5%2588%25A4%25E6%2596%25ADhttp%2520%25E5%2592%258Chttps%25E9%2593%25BE%25E6%258E%25A5%25E6%2598%25AF%25E5%2590%25A6%25E6%259C%2589%25E6%2595%2588&rsv_pq=9f44e0ab00003921&rsv_t=75049vdEDRuk5jCqPj%2B0%2BHL1%2FwjNDb0713wojum9JR2Hy7gxLbMsAgFzxQT3bgwhWtmK&rqlang=cn&rsv_enter=1&inputT=6088&rsv_sug3=19&rsv_sug1=11&rsv_sug7=000&rsv_sug2=0&prefixsug=java%25E5%2588%25A4%25E6%2596%25ADurl%25E6%2598%25AF%25E5%2590%25A6%25E6%259C%2589%25E6%2595%2588&rsp=0&rsv_sug4=6745&rsv_sug=1",3000);
		//System.out.println(a);
		System.out.println(CheckUrlUtil.checkBDY("https://pan.baidu.com/s/1jL8xjMzFvPEYnNsK5q4uGQ"));
	}
	
}
