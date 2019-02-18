package com.jyb.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jyb.entity.DataDictionary;
import com.jyb.repository.DataDictionaryRepositroy;
import com.jyb.service.DataDictionaryService;
import com.jyb.util.StringUtil;

@Service("dataDictionaryService")
@Transactional
public class DataDictionaryServiceImpl implements DataDictionaryService {
	
   @Autowired
   private DataDictionaryRepositroy dataDictionaryRepositroy;
	
	@Override
	public List<DataDictionary> listAll(final DataDictionary dataDictionary, Direction direction, String... properties) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(direction,properties);
		List<DataDictionary> dataDictionaryList = dataDictionaryRepositroy.findAll(new Specification<DataDictionary>() {

			@Override
			public Predicate toPredicate(Root<DataDictionary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				  List<Predicate> list = new ArrayList<Predicate>();
				  if(dataDictionary!=null){
					  if(StringUtil.isNotEmpty(dataDictionary.getDictionaryType())){
						   list.add(cb.equal(root.get("dictionaryType"), dataDictionary.getDictionaryType()));
					  }
				  }
				  Predicate[] p = new Predicate[list.size()];
		          return cb.and(list.toArray(p));
			}
		}, sort);
		return dataDictionaryList;
	}

	
	@Override
	public List<DataDictionary> listPage(final DataDictionary dataDictionary, Integer page, Integer pageSize,
			Direction direction, String... properties) {
		// TODO Auto-generated method stub
		Pageable pageable = new PageRequest(page-1, pageSize, direction, properties);
		Page<DataDictionary> pageDataDictionary = dataDictionaryRepositroy.findAll(new Specification<DataDictionary>() {
			@Override
			public Predicate toPredicate(Root<DataDictionary> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate =  cb.conjunction();
				if(dataDictionary!=null){
				     if(dataDictionary.getId()!=null){
							predicate.getExpressions().add(cb.equal(root.get("id"),dataDictionary.getId() ));
					 }
				     if(StringUtil.isNotEmpty(dataDictionary.getDictionaryType())){
							predicate.getExpressions().add(cb.equal(root.get("dictionaryType"),dataDictionary.getDictionaryType() ));
					 }	
				}
				return predicate;
			}
		},pageable);
		return pageDataDictionary.getContent();
	}
	
	
	@Override
	public Long getCount(final DataDictionary dataDictionary) {
		// TODO Auto-generated method stub
		Long count = dataDictionaryRepositroy.count(new Specification<DataDictionary>() {
			@Override
			public Predicate toPredicate(Root<DataDictionary> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate =  cb.conjunction();
				if(dataDictionary!=null){
					 if(dataDictionary.getId()!=null){
							predicate.getExpressions().add(cb.equal(root.get("id"),dataDictionary.getId() ));
					 }
				     if(dataDictionary.getDictionaryType()!=null){
							predicate.getExpressions().add(cb.equal(root.get("dictionaryType"),dataDictionary.getDictionaryType() ));
					 }	
				}
				return predicate;
			}
		});
		return count;
	}


	@Override
	public DataDictionary getId(Integer id) {
		// TODO Auto-generated method stub
		return dataDictionaryRepositroy.getOne(id);
	}


	@Override
	public void save(DataDictionary dataDictionary) {
		// TODO Auto-generated method stub
		dataDictionaryRepositroy.save(dataDictionary);
	}


	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		dataDictionaryRepositroy.delete(id);
	}


	@Override
	public List<DataDictionary> findByDictionaryType(String dictionaryType) {
		// TODO Auto-generated method stub
		return dataDictionaryRepositroy.findByDictionaryType(dictionaryType);
	}

}
