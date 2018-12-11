package com.jyb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jyb.entity.DataDictionary;


public interface DataDictionaryRepositroy extends JpaRepository<DataDictionary,Integer>,JpaSpecificationExecutor<DataDictionary>{

}
