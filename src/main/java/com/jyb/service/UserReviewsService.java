package com.jyb.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.jyb.entity.UserReviews;

public interface UserReviewsService {

	/**
	 * 根据条件分页查询评论信息
	 * @param userReviews
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<UserReviews> listPage(UserReviews userReviews,Integer page,Integer pageSize,Direction direction, String... properties);
	
	/**
	 * 根据条件获取总记录数
	 * @param userReviews
	 * @return
	 */
	public Long getCount(UserReviews userReviews);
	
	/**
	 * 保存评论
	 * @param userReviews
	 */
	public void save(UserReviews userReviews);
	
	
	/**
	 * 删除评论
	 * @param id
	 */
	public void delete(Integer id);
	
	/**
	 * 根据id获取实体
	 * @param id
	 * @return
	 */
	public UserReviews getId(Integer id);
}
