package com.jyb.service;

import com.jyb.entity.Log;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

public interface LogService {
	 /**
	   * 根据条件分页查询信息
	   * @param log
	   * @param page
	   * @param pageSize
	   * @param direction
	   * @param properties
	   * @return
	   */
	  public List<Log>  listPage(Log log,Integer page,Integer pageSize,Direction direction,String...properties);

	  /**
	   * 根据条件查询所有总记录数
	   * @param log
	   * @return
	   */
	  public Long getCount(Log log);

	  /**
	   * 添加
	   * @param log
	   */
	  public void save(Log log);

	  /**
	   * 删除
	   * @param id
	   */
	  public void delete(Integer id);

}
