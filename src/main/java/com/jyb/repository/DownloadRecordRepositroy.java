package com.jyb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jyb.entity.DownloadRecord;

public interface DownloadRecordRepositroy  extends JpaRepository<DownloadRecord,Integer>,JpaSpecificationExecutor<DownloadRecord>{

	/**
	 * 查询某个用户下载某个资源的次数
	 * @param userId
	 * @return
	 */
	@Query(value="select count(*) from download_record where user_id=?1 and resource_id=?2 and large_category=?3 ",nativeQuery=true)
	public Integer getDownloadsFrequency(Integer userId,Integer resourceId,String largeCategory);
	
	/**
	 * 删除指定资源的下载记录
	 * @param articleId
	 */
	@Query(value="delete from download_record where resource_id=?1 and large_category=?2",nativeQuery=true)
	@Modifying
	public void deleteDownloadRecord(Integer articleId,String largeCategory);
	
	/**
	 * 根据资源ID和大类别查询已下载的记录
	 * @param resourceId
	 * @param largeCategory
	 * @return
	 */
	@Query(value="select * from download_record where resource_id=?1 and large_category=?2",nativeQuery=true)
	public DownloadRecord getDownloadedRecord(Integer resourceId,String largeCategory);
	
}
