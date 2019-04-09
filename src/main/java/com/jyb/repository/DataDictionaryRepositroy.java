package com.jyb.repository;

import com.jyb.entity.DataDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DataDictionaryRepositroy extends JpaRepository<DataDictionary,Integer>,JpaSpecificationExecutor<DataDictionary>{
	/**
	 * 根据大类别查询数据
	 * @param dictionaryType
	 * @return   List<DataDictionary>
	 */
	@Query(value="select * from data_dictionary where dictionary_type=?1",nativeQuery=true)
	public List<DataDictionary> findByDictionaryType(String dictionaryType);


}
