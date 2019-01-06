package com.jyb.service;

import com.jyb.entity.UserInformation;

public interface UserInformationService {
    /**
     * 添加或者修改用户信息
     * @param userInformation
     */
	public void save(UserInformation userInformation);

	/**
	 * 验证用户名是否重复
	 * @param userName
	 * @return
	 */
	public UserInformation checkUserName(String userName);

	/**
	 * 验证邮箱是否重复
	 * @param email
	 * @return
	 */
	public UserInformation checkEmail(String email);


}
