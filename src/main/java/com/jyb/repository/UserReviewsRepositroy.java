package com.jyb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jyb.entity.UserReviews;

public interface UserReviewsRepositroy extends JpaRepository<UserReviews, Integer>,JpaSpecificationExecutor<UserReviews>{
	/**
	 * 根据资源Id和类别删除评论
	 * @param resourceId
	 * @param largeCategory
	 */
	@Query(value="delete from user_reviews where resource_id=?1 and large_category=?2",nativeQuery=true)
	@Modifying
	public void deleteUserReviews(Integer resourceId,String largeCategory);
	
	/**
	 * 根据资源Id和类别查找
	 * @param resourceId
	 * @param largeCategory
	 */
	@Query(value="select *  from user_reviews where resource_id=?1 and large_category=?2",nativeQuery=true)
	public List<UserReviews> ReviewsList(Integer resourceId,String largeCategory);
	
}
