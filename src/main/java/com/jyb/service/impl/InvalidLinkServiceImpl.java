package com.jyb.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jyb.entity.InvalidLink;
import com.jyb.repository.InvalidLinkRepositroy;
import com.jyb.service.InvalidLinkService;
@Service("invalidLinkService")
public class InvalidLinkServiceImpl implements InvalidLinkService{
 
	@Autowired
	private InvalidLinkRepositroy invalidLinkRepositroy;
	
	@Override
	public List<InvalidLink> listPage(InvalidLink invalidLink, Integer page, Integer pageSize, Direction direction,
			String... properties) {
		// TODO Auto-generated method stub
		Pageable pageable = new PageRequest(page-1, pageSize, direction, properties);
		Page<InvalidLink> pageInvalidLink = invalidLinkRepositroy.findAll(new Specification<InvalidLink>() {
			@Override
			public Predicate toPredicate(Root<InvalidLink> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate =  cb.conjunction();
				if(invalidLink!=null){
					if(invalidLink.getUserId()!=null){
						predicate.getExpressions().add(cb.equal(root.get("userId"), invalidLink.getUserId()));
					}
				}
				return predicate;
			}
		},pageable);
		return pageInvalidLink.getContent();
	}

	@Override
	public Long getCount(InvalidLink invalidLink) {
		// TODO Auto-generated method stub
		Long count = invalidLinkRepositroy.count(new Specification<InvalidLink>() {
			@Override
			public Predicate toPredicate(Root<InvalidLink> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate =  cb.conjunction();
				if(invalidLink!=null){
					if(invalidLink.getUserId()!=null){
						predicate.getExpressions().add(cb.equal(root.get("userId"), invalidLink.getUserId()));
					}
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	public void save(InvalidLink invalidLink) {
		// TODO Auto-generated method stub
		invalidLinkRepositroy.save(invalidLink);
	}

	@Override
	public InvalidLink getId(Integer id) {
		// TODO Auto-generated method stub
		return invalidLinkRepositroy.getOne(id);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		invalidLinkRepositroy.delete(id);
	}

}
