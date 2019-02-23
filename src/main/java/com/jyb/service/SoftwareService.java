package com.jyb.service;

import java.util.List;
import org.springframework.data.domain.Sort.Direction;

import com.jyb.entity.GameInformation;
import com.jyb.entity.Software;

public interface SoftwareService {

		
	
	  /**
	   * 查所信息
	   * @return
	   */
	   public List<Software> listAll(Software software,Direction direction,String...properties);
		
	
	  /**
	   * 根据条件分页查询信息
	   * @param gameInfo
	   * @param page
	   * @param pageSize
	   * @param direction
	   * @param properties
	   * @return
	   */
	  public List<Software>  listPage( Software software,Integer page,Integer pageSize,Direction direction,String...properties);
	  
	  /**
	   * 根据条件查询所有总记录数
	   * @param gameInfo
	   * @return
	   */
	  public Long getCount( Software  software);
	  
	  /**
	   * 根据Id查找信息
	   * @param id
	   * @return
	   */
	  public  Software getId(Integer id);
	  
	  /**
	   * 添加或者修改信息
	   * @param gameInfo
	   */
	  public void save( Software  software);
	  
	  /**
	   * 删除信息
	   * @param id
	   */
	  public void delete(Integer id);
}
