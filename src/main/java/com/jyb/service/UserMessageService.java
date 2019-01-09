package com.jyb.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.jyb.entity.GameInformation;
import com.jyb.entity.UserMessage;

public interface UserMessageService {

	/**
	 * 分页查询用户留言信息
	 * 
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<UserMessage> listPage(Integer page, Integer pageSize, Direction direction, String... properties);

	/**
	 * 查询所有留言总记录数
	 * 
	 * @param gameInfo
	 * @return
	 */
	public Long getCount();

	/**
	 * 添加用户留言信息
	 * 
	 * @param userInformation
	 */
	public void save(UserMessage userMessage);

}
