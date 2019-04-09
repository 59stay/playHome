package com.jyb.service;

import com.jyb.entity.DataDictionary;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

public interface DataDictionaryService {
	/**
	 *  查询所有的集合数据
	 * @param dataDictionary
	 * @param direction
	 * @param properties
	 * @return
	 */
   public List<DataDictionary> listAll(DataDictionary dataDictionary,Direction direction,String...properties);

   /**
    * 根据条件查询所有类别记录信息，并分页
    * @param dataDictionary
    * @param page
    * @param pageSize
    * @param direction
    * @param properties
    * @return
    */
	public List<DataDictionary> listPage(DataDictionary dataDictionary, Integer page, Integer pageSize,
			Direction direction, String... properties);

	/**
	 * 根据条件查询所有集合记录总记录数
	 *
	 * @param dataDictionary
	 * @return
	 */
	public Long getCount(DataDictionary dataDictionary);
	/**
	 * 根据Id获取实体信息
	 * @param id
	 * @return
	 */
	public DataDictionary getId(Integer id);

	  /**
	   * 添加或者修改信息
	   * @param dataDictionary
	   */
	  public void save(DataDictionary dataDictionary);

	  /**
	   * 删除信息
	   * @param id
	   */
	  public void delete(Integer id);
	  /**
	   * 根据大类别查类别
	   * @param dictionaryType
	   * @return
	   */
	  public List<DataDictionary> findByDictionaryType(String dictionaryType);
}
