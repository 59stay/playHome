package com.jyb.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jyb.entity.DataDictionary;
import com.jyb.repository.DataDictionaryRepositroy;
import com.jyb.service.DataDictionaryService;

@Service("dataDictionaryService")
public class DataDictionaryServiceImpl implements DataDictionaryService {
	
   @Autowired
   private DataDictionaryRepositroy dataDictionaryRepositroy;
	
	@Override
	public List<DataDictionary> listAll(DataDictionary dataDictionary, Direction direction, String... properties) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(direction,properties);
		List<DataDictionary> dataDictionaryList = dataDictionaryRepositroy.findAll(new Specification<DataDictionary>() {

			@Override
			public Predicate toPredicate(Root<DataDictionary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				  List<Predicate> list = new ArrayList<Predicate>();
				  if(dataDictionary!=null){
					  if(dataDictionary.getDictionaryType()!=null){
						   list.add(cb.equal(root.get("dictionaryType"), dataDictionary.getDictionaryType()));
					  }
				  }
				  Predicate[] p = new Predicate[list.size()];
		          return cb.and(list.toArray(p));
			}
		}, sort);
		return dataDictionaryList;
	}

}
