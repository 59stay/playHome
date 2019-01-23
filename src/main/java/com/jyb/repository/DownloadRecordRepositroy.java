package com.jyb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jyb.entity.DownloadRecord;

public interface DownloadRecordRepositroy  extends JpaRepository<DownloadRecord,Integer>,JpaSpecificationExecutor<DownloadRecord>{

	/**
	 * 查询某个用户下载某个资源的次数
	 * @param userId
	 * @return
	 */
	@Query(value="select count(*) from download_record where user_id=?1 and resource_id=?2 and large_category=?3 ",nativeQuery=true)
	public Integer getDownloadTime(Integer userId,Integer resourceId,String largeCategory);
	
	/**
	 * 删除指定资源的下载信息
	 * @param articleId
	 */
	@Query(value="delete from download_record where resource_id=?1",nativeQuery=true)
	@Modifying
	public void deleteByResourceId(Integer articleId);
	
	
}
