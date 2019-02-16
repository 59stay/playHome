package com.jyb.util;

import java.security.MessageDigest;

import org.apache.shiro.crypto.hash.Md5Hash;

import com.jyb.specialEntity.Constant;


/**
 * Md5工具类
 * 
 * @author jyb
 *
 */
public class MD5Util {
	
	/**
	 * Md5加密-带盐值
	 * @param str
	 * @param salt
	 * @return
	 */
	public static String md5(String str,String salt){
		return new Md5Hash(str,salt).toString();
	}
	

	public static void main(String[] args) {
		String password="123456";
		System.out.println("Md5加密："+MD5Util.md5(password, Constant.SALT));
	}
	
}
