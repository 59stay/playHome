package com.jyb.service;

import com.jyb.entity.Timer;

import java.util.List;

public interface TimerService {
	/**
	 * 查询所有信息
	 * @return    List<Timer>
	 */
	public List<Timer> listAll();

	/**
	 * 添加或者修改信息
	 *
	 * @param timer
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
