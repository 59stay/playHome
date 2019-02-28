package com.jyb.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.jyb.entity.DownloadRecord;

public interface DownloadRecordService {
	/**
	 * 根据条件查询所有下载记录信息，并分页
	 * 
	 * @param downloadRecord
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<DownloadRecord> listPage(DownloadRecord downloadRecord, Integer page, Integer pageSize,
			Direction direction, String... properties);

	/**
	 * 根据条件查询所有下载记录总记录数
	 * 
	 * @param downloadRecord
	 * @return
	 */
	public Long getCount(DownloadRecord downloadRecord);
	
	/**
	 * 查询某个用户下载某个资源的次数
	 * @param userId
	 * @param resourceId
	 * @return
	 */
	public Integer getDownloadsFrequency(Integer userId,Integer resourceId,String largeCategory);

	/**
	 * 添加下载记录
	 * 
	 * @param downloadRecord
	 */
	public void save(DownloadRecord downloadRecord);

	/**
	 * 根据Id查找下载记录
	 * 
	 * @param id
	 * @return
	 */
	public DownloadRecord getId(Integer id);

	/**
	 * 根据id删除下载记录
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 根据资源id和大类别删除下载记录
	 * @param articleId
	 * @param largeCategory
	 */
	public void deleteDownloadRecord(Integer articleId,String largeCategory);
	
	/**
	 * 根据资源ID和大类别查询已下载的记录
	 * @param resourceId
	 * @param largeCategory
	 * @return
	 */
	public DownloadRecord getDownloadedRecord(Integer resourceId,String largeCategory);
	
	

}
