package com.jyb.service.impl;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jyb.entity.SignIn;
import com.jyb.repository.SignInRepositroy;
import com.jyb.service.SignInService;

@Service("signInService")
@Transactional
public class SignInServiceImpl implements SignInService {

	@Autowired
	private SignInRepositroy signInRepositroy;
	@Override
	public Long getCount(SignIn signIn) {
		// TODO Auto-generated method stub
		Long count = signInRepositroy.count(new Specification<SignIn>() {
			@Override
			public Predicate toPredicate(Root<SignIn> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate =  cb.conjunction();
				if(signIn!=null){
					if (signIn.getUserInformation() != null
							&& signIn.getUserInformation().getId() != null) {
						predicate.getExpressions().add(cb.equal(root.get("userInformation").get("id"),
								signIn.getUserInformation().getId()));
					}
					if(signIn.getSignInTime()!=null ){
							predicate.getExpressions().add(cb.like(root.get("signInTime"),signIn.getSignInTime()+"%"));
					}
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	public SignIn getId(Integer id) {
		// TODO Auto-generated method stub
		return signInRepositroy.getOne(id);
	}

	@Override
	public void save(SignIn signIn) {
		// TODO Auto-generated method stub
		signInRepositroy.save(signIn);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		signInRepositroy.delete(id);
	}


	@Override
	public SignIn getUserId(Integer userId, String signInTime) {
		// TODO Auto-generated method stub
		return signInRepositroy.getUserId(userId, signInTime);
	}

}
