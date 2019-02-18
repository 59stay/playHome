package com.jyb.service.impl;

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
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jyb.entity.GameInformation;
import com.jyb.entity.UserInformation;
import com.jyb.repository.UserInformationRepositroy;
import com.jyb.service.UserInformationService;
import com.jyb.util.StringUtil;

@Service("userInformationService")
@Transactional
public class UserInformationServiceImpl implements UserInformationService {
	@Autowired
	private UserInformationRepositroy userInformationRepositroy;
	
	@Override
	public void save(UserInformation userInformation) {
		// TODO Auto-generated method stub
		userInformationRepositroy.save(userInformation);
	}

	@Override
	public UserInformation findByUserName(String userName) {
		// TODO Auto-generated method stub
		return userInformationRepositroy.findByUserName(userName);
	}

	@Override
	public UserInformation findByEmail(String email) {
		// TODO Auto-generated method stub
		return userInformationRepositroy.findByEmail(email);
	}

	@Override
	public UserInformation getById(Integer id) {
		// TODO Auto-generated method stub
		return userInformationRepositroy.getOne(id);
	}

	@Override
	public List<UserInformation> listPage(final UserInformation userInfo, Integer page, Integer pageSize, Direction direction,
			String... properties) {
		// TODO Auto-generated method stub
		Pageable pageable = new PageRequest(page-1, pageSize, direction, properties);
		Page<UserInformation> pageUserInformation = userInformationRepositroy.findAll(new Specification<UserInformation>() {
			@Override
			public Predicate toPredicate(Root<UserInformation> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate =  cb.conjunction();
				if(userInfo!=null){
					if(StringUtil.isNotEmpty(userInfo.getUserName())){
						predicate.getExpressions().add(cb.like(root.<String>get("userName"), "%"+userInfo.getUserName().trim()+"%"));
					}
					if(StringUtil.isNotEmpty(userInfo.getEmail())){
						predicate.getExpressions().add(cb.like(root.<String>get("email"), "%"+userInfo.getEmail().trim()+"%"));
					}

				}
				return predicate;
			}
		},pageable);
		return pageUserInformation.getContent();
	}

	@Override
	public Long getCount(final UserInformation userInfo) {
		// TODO Auto-generated method stub
		Long count = userInformationRepositroy.count(new Specification<UserInformation>() {
			@Override
			public Predicate toPredicate(Root<UserInformation> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate =  cb.conjunction();
				if(userInfo!=null){
					if(StringUtil.isNotEmpty(userInfo.getUserName())){
						predicate.getExpressions().add(cb.like(root.<String>get("userName"), "%"+userInfo.getUserName().trim()+"%"));
					}
					if(StringUtil.isNotEmpty(userInfo.getEmail())){
						predicate.getExpressions().add(cb.like(root.<String>get("email"), "%"+userInfo.getEmail().trim()+"%"));
					}
				}
				return predicate;
			}
		});
		return count;
	}

	
	
}
