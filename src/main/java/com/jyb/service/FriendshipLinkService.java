package com.jyb.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.jyb.entity.DataDictionary;
import com.jyb.entity.FriendshipLink;
import com.jyb.entity.UserInformation;

public interface FriendshipLinkService {
	/**
	 * 查询所有信息
	 * 
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<FriendshipLink> listAll(Direction direction, String... properties);

	/**
	 * 添加或者修改信息
	 * 
	 * @param userInformation
	 */
	public void save(FriendshipLink friendshipLink);

	/**
	 * 根据id查找实体
	 * 
	 * @param id
	 * @return
	 */
	public FriendshipLink getById(Integer id);

	/**
	 * 删除信息
	 * 
	 * @param id
	 */
	public void delete(Integer id);

}
