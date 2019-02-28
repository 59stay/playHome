package com.jyb.util;


import java.io.IOException;
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
	public static boolean checkBDY(String link){
		CloseableHttpResponse response = null;
		try {
		HttpGet httpget = new HttpGet(link); 
		httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0"); // 设置请求头消息User-Agent
		
		
			response = httpClient.execute(httpget);
		} catch (Exception e) {
			return false;		
		}
		HttpEntity entity=response.getEntity(); // 获取返回实体  
		String result="";
		try {
			result = EntityUtils.toString(entity, "utf-8");
		} catch (IOException e) {
			return false;	
		}
		if(result.contains("请输入提取码")||result.contains("分享无限制")){
			return true;
		}else{
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
        long startTime = System.currentTimeMillis();
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
                  break;  
             } catch (Exception e) { 
             	  counts++;  
                  url = null;  
                  continue;  
             } 
        }
        log.info("=======================总耗时"+(System.currentTimeMillis()-startTime)*0.001+"秒============================"); 
		return flag;
    }
	public static void main(String[] args) throws Exception {
		//boolean a = CheckUrlUtil.checkUrl("http://ww.bai.com",3000);
		//System.out.println(a);
		//System.out.println(CheckUrlUtil.checkBDY("https://www.baidu.com https://www.baidu.com, https://www.baidu.com,,"));
	}
	
}
