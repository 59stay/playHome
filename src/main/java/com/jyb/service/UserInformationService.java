package com.jyb.service;

import com.jyb.entity.UserInformation;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

public interface UserInformationService {

	 /**
	   * 根据条件分页查询用户信息
	   * @param userInfo
	   * @param page
	   * @param pageSize
	   * @param direction
	   * @param properties
	   * @return
	   */
	  public List<UserInformation>  listPage(UserInformation userInfo,Integer page,Integer pageSize,Direction direction,String...properties);

	  /**
	   * 根据条件查询所有用户总记录数
	   * @param userInfo
	   * @return
	   */
	  public Long getCount(UserInformation userInfo);



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
	public UserInformation findByUserName(String userName);

	/**
	 * 验证邮箱是否重复
	 * @param email
	 * @return
	 */
	public UserInformation findByEmail(String email);


	/**
	 * 根据id查找实体
	 * @param id
	 * @return
	 */
	public UserInformation getById(Integer id);


}
