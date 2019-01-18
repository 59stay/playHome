package com.jyb.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.jyb.entity.GameInformation;
import com.jyb.entity.InvalidLink;

public interface InvalidLinkService {
     /**
      * 根据条件查询所有失效链接信息，并分页
      * @param invalidLink
      * @param page
      * @param pageSize
      * @param direction
      * @param properties
      * @return
      */
	 public List<InvalidLink>  listPage(InvalidLink invalidLink,Integer page,Integer pageSize,Direction direction,String...properties);

	 /**
	  * 根据条件查询所有失效链接总记录数
	  * @param invalidLink
	  * @return
	  */
	 public Long getCount(InvalidLink invalidLink);

	 /**
	  * 添加失效链接
	  * @param invalidLink
	  */
	 public void save(InvalidLink invalidLink);	 
	 
	 /**
	   * 根据Id查找失效链接
	   * @param id
	   * @return
	   */
	  public InvalidLink getId(Integer id);
	  
	 
}
