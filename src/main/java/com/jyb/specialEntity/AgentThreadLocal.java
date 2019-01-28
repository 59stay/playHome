package com.jyb.specialEntity;

import com.jyb.entity.UserInformation;

/**
 * @description : 用于存储用户信息
 * @author jyb
 * @date : Create in 2019年1月26日22:44:30
 */
public class AgentThreadLocal {

	private static final ThreadLocal<UserInformation> LOCAL = new ThreadLocal<UserInformation>();

	public static void set(UserInformation userInformation) {
		LOCAL.set(userInformation);
	}

	public static UserInformation get() {
		return LOCAL.get();
	}

	public static void remove() {
		LOCAL.remove();
	}
	
	
}
