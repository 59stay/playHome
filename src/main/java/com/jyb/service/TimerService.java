package com.jyb.service;

import java.util.List;

import com.jyb.entity.SignIn;
import com.jyb.entity.Timer;

public interface TimerService {
	/**
	 * 查询所有信息
	 * 
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<Timer> listAll();

	/**
	 * 添加或者修改信息
	 * 
	 * @param userInformation
	 */
	public void save(Timer timer);
	
  /**
   * 根据Id查
   * @param id
   * @return
   */
  public Timer getId(Integer id);
	
  /**
   * 删除信息
   * @param id
   */
  public void delete(Integer id);

}
