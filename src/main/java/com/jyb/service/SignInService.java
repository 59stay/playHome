package com.jyb.service;

import java.util.Date;

import com.jyb.entity.SignIn;

public interface SignInService {
	 /**
	   * 根据条件分页查询签到信息
	   * @param gameInfo
	   * @param page
	   * @param pageSize
	   * @param direction
	   * @param properties
	   * @return
	   */
	//  public List<SignIn>  listPage(SignIn signIn,Integer page,Integer pageSize,Direction direction,String...properties);
	  
	  /**
	   * 根据条件查询所有签到信息总记录数
	   * @param gameInfo
	   * @return
	   */
 	  public Long getCount(SignIn signIn);
	  
	  
	  /**
	   * 根据Id查找签到信息
	   * @param id
	   * @return
	   */
	  public SignIn getId(Integer id);
	  
	  /**
	   * 根据用户id和签到时间查找签到信息
	   * @param userId
	   * @param signInTime
	   * @return
	   */
	  public SignIn  getUserId(Integer userId,String signInTime);
	  
	  /**
	   * 添加或者修改签到信息
	   * @param gameInfo
	   */
	  public void save(SignIn signIn);
	  
	  /**
	   * 删除签到信息
	   * @param id
	   */
	  public void delete(Integer id);
}
