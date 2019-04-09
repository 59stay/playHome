package com.jyb.service;

import com.jyb.entity.UserMessage;
import org.springframework.data.domain.Sort.Direction;

import java.util.Date;
import java.util.List;

public interface UserMessageService {

	/**
	 * 分页查询用户留言信息
	 *
	 * @param properties
	 * @return
	 */
	public List<UserMessage> listPage(UserMessage userMessage,Integer page, Integer pageSize, Direction direction, String... properties);

	/**
	 * 查询所有留言总记录数
	 *
	 * @return
	 */
	public Long getCount(UserMessage userMessage);

	/**
	 * 添加用户留言信息
	 *
	 * @param userMessage
	 */
	public void save(UserMessage userMessage);
	/**
	 * 删除留言信息
	 * @param id
	 */
	 public void delete(Integer id);
	 /**
	  * 根据日期获取当天某用户留言总记录数
	  * @param userId
	  * @param startDate
	  * @param endDate
	  * @return
	  */
	 public Integer getByDate(Integer userId,Date startDate,Date endDate);

}
