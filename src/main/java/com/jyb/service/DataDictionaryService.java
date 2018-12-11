package com.jyb.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.jyb.entity.DataDictionary;

public interface DataDictionaryService {
	/**
	 *  查询所有的集合数据 
	 * @param dataDictionary
	 * @param direction
	 * @param properties
	 * @return
	 */
   public List<DataDictionary> listAll(DataDictionary dataDictionary,Direction direction,String...properties);
}
