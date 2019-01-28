package com.jyb.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * @author Administrator
 *
 */
public class DateUtil {

	/**
	 * 日期对象转字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
	/**
	 * 字符串转日期对象
	 * @param str
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static Date formatString(String str,String format) throws Exception{
		if(StringUtil.isEmpty(str)){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}
	
	public static String getCurrentDateStr()throws Exception{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
		return sdf.format(date);
	}
	
	public static String getCurrentDatePath()throws Exception{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd/");
		return sdf.format(date);
	}
	
	 /**
	  * 将yyyy-MM-dd HH-mm-ss截取成 yyyy-MM-dd 
	  * @param date
	  * @param geshi
	  * @return
	  */
	 public static String toTimestamp(String date,String geshi){ SimpleDateFormat df=new SimpleDateFormat(geshi);
		     Date s = null;
		     String result=null;
		     try { s = df.parse(date);
		         result=df.format(s);
		     } catch (Exception e) {
		         e.printStackTrace();
		     } return result;
    }
	 /**
	  * 将yyyy-MM-dd HH-mm-ss截取成 yyyy-MM-dd 
	  * @param date
	  * @param geshi
	  * @return
	  */
     public static String toTimestamp(Date date,String geshi){ SimpleDateFormat df=new SimpleDateFormat(geshi);
      Date s = date;
      String result=null;
      result=df.format(s);
      return result;
   }
 
	
	public static void main(String[] args) {
		try {
			 String s=DateUtil.toTimestamp("2017-03-23 10:57:57","yyyy-MM-dd");
			 System.out.println(s);
			//System.out.println(getCurrentDatePath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
