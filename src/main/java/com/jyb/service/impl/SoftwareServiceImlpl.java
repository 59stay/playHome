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

import com.jyb.entity.GameInformation;
import com.jyb.entity.Software;
import com.jyb.repository.SoftwareRepositroy;
import com.jyb.service.SoftwareService;
import com.jyb.util.StringUtil;

@Service("softwareService")
@Transactional
public class SoftwareServiceImlpl implements SoftwareService{

	@Autowired
	private SoftwareRepositroy softwareRepositroy;
	
	
	

	@Override
	public List<Software> listAll(Software software, Direction direction, String... properties) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(direction,properties);
		List<Software> result = softwareRepositroy.findAll(new Specification<Software>() {
			@Override
			public Predicate toPredicate(Root<Software> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				  // TODO Auto-generated method stub
				  List<Predicate> list = new ArrayList<Predicate>();
				  if (software!=null&&software.getAuditStatus()!=null) {
					  list.add(cb.equal(root.get("auditStatus"),software.getAuditStatus()));
					  
				  }
				  Predicate[] p = new Predicate[list.size()];
		          return cb.and(list.toArray(p));
			}
			
		  },sort);
		return result;
	}
	
	@Override
	public List<Software> listPage(Software software, Integer page, Integer pageSize, Direction direction,
			String... properties) {
		// TODO Auto-generated method stub
		Pageable pageable = new PageRequest(page-1, pageSize, direction, properties);
		Page<Software> pageSoftware = softwareRepositroy.findAll(new Specification<Software>() {
			@Override
			public Predicate toPredicate(Root<Software> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate =  cb.conjunction();
				if(software!=null){
					if(software.getUserInformation()!=null && StringUtil.isNotEmpty(software.getUserInformation().getUserName())){
						predicate.getExpressions().add(cb.like(root.<String>get("userInformation").<String>get("userName"), "%"+software.getUserInformation().getUserName()+"%"));
					}
					if(StringUtil.isNotEmpty(software.getName())){
						predicate.getExpressions().add(cb.like(root.<String>get("name"), "%"+software.getName().trim()+"%"));
					}
					if(software.getAuditStatus()!=null){
						predicate.getExpressions().add(cb.equal(root.get("auditStatus"), software.getAuditStatus()));
					}
					if(software.getIsUseful()!=null){
						predicate.getExpressions().add(cb.equal(root.get("isUseful"), software.getIsUseful()));
					}
				    if(software.getDataDictionary()!=null && software.getDataDictionary().getId()!=null ){
						predicate.getExpressions().add(cb.equal(root.get("dataDictionary").get("id"),software.getDataDictionary().getId() ));
					}	
				}
				return predicate;
			}
		},pageable);
		return pageSoftware.getContent();
	}

	@Override
	public Long getCount(Software software) {
		// TODO Auto-generated method stub
		Long count = softwareRepositroy.count(new Specification<Software>() {
			@Override
			public Predicate toPredicate(Root<Software> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate =  cb.conjunction();
				if(software!=null){
					if(software.getUserInformation()!=null && StringUtil.isNotEmpty(software.getUserInformation().getUserName())){
						predicate.getExpressions().add(cb.like(root.<String>get("userInformation").<String>get("userName"), "%"+software.getUserInformation().getUserName()+"%"));
					}
					if(StringUtil.isNotEmpty(software.getName())){
						predicate.getExpressions().add(cb.like(root.<String>get("name"), "%"+software.getName().trim()+"%"));
					}
					if(software.getAuditStatus()!=null){
						predicate.getExpressions().add(cb.equal(root.get("auditStatus"), software.getAuditStatus()));
					}
					if(software.getIsUseful()!=null){
						predicate.getExpressions().add(cb.equal(root.get("isUseful"), software.getIsUseful()));
					}
				    if(software.getDataDictionary()!=null && software.getDataDictionary().getId()!=null ){
						predicate.getExpressions().add(cb.equal(root.get("dataDictionary").get("id"),software.getDataDictionary().getId() ));
					}	
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	public Software getId(Integer id) {
		// TODO Auto-generated method stub
		return softwareRepositroy.getOne(id);
	}

	@Override
	public void save(Software software) {
		// TODO Auto-generated method stub
		 softwareRepositroy.save(software);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		softwareRepositroy.delete(id);
	}


}
