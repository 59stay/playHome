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

import com.jyb.entity.UserInformation;
import com.jyb.entity.UserReviews;
import com.jyb.repository.UserReviewsRepositroy;
import com.jyb.service.UserReviewsService;
import com.jyb.util.StringUtil;

@Service("userReviewsService")
@Transactional
public class UserReviewsServiceImpl implements UserReviewsService {

	@Autowired
	private UserReviewsRepositroy userReviewsRepositroy;

	@Override
	public List<UserReviews> listPage(final UserReviews userReviews, Integer page, Integer pageSize, Direction direction,
			String... properties) {
		// TODO Auto-generated method stub
		Pageable pageable = new PageRequest(page - 1, pageSize, direction, properties);
		Page<UserReviews> pageComment = userReviewsRepositroy.findAll(new Specification<UserReviews>() {

			@Override
			public Predicate toPredicate(Root<UserReviews> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (userReviews != null) {
					if (userReviews.getUserInformation() != null
							&& userReviews.getUserInformation().getId() != null&& userReviews.getUserInformation().getUserRole()!=0) {
						predicate.getExpressions().add(cb.equal(root.get("userInformation").get("id"),
								userReviews.getUserInformation().getId()));
					}
					if (userReviews.getUserInformation() != null && StringUtil.isNotEmpty(userReviews.getUserInformation().getUserName())) {
						predicate.getExpressions().add(cb.like(root.<UserInformation>get("userInformation").<String>get("userName"),"%"+userReviews.getUserInformation().getUserName()+"%"));
					}
					if (StringUtil.isNotEmpty(userReviews.getLargeCategory())) {
						predicate.getExpressions().add(cb.equal(root.get("largeCategory"), userReviews.getLargeCategory()));
					
					}
					if(userReviews.getResourceId() != null){
						predicate.getExpressions().add(cb.equal(root.get("resourceId"), userReviews.getResourceId()));
					}
					if(userReviews.getResourceId() != null){
						predicate.getExpressions().add(cb.equal(root.get("publisherId"), userReviews.getPublisherId()));
					}
					
					if(StringUtil.isNotEmpty(userReviews.getReviewsContent())){
						predicate.getExpressions().add(cb.like(root.<String>get("reviewsContent"), "%"+userReviews.getReviewsContent().trim()+"%"));
					}
				}
				return predicate;
			}
		}, pageable);
		return pageComment.getContent();
	}

	@Override
	public Long getCount(final UserReviews userReviews) {
		// TODO Auto-generated method stub
		Long count = userReviewsRepositroy.count(new Specification<UserReviews>() {

			@Override
			public Predicate toPredicate(Root<UserReviews> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (userReviews != null) {
					if (userReviews.getUserInformation() != null
							&& userReviews.getUserInformation().getId() != null&& userReviews.getUserInformation().getUserRole()!=0) {
						predicate.getExpressions().add(cb.equal(root.get("userInformation").get("id"),
								userReviews.getUserInformation().getId()));
					}
					if (userReviews.getUserInformation() != null && StringUtil.isNotEmpty(userReviews.getUserInformation().getUserName())) {
						predicate.getExpressions().add(cb.like(root.<UserInformation>get("userInformation").<String>get("userName"),"%"+userReviews.getUserInformation().getUserName()+"%"));
					}
					if (StringUtil.isNotEmpty(userReviews.getLargeCategory())) {
						predicate.getExpressions().add(cb.equal(root.get("largeCategory"), userReviews.getLargeCategory()));
					
					}
					if(userReviews.getResourceId() != null){
						predicate.getExpressions().add(cb.equal(root.get("resourceId"), userReviews.getResourceId()));
					}
					if(userReviews.getResourceId() != null){
						predicate.getExpressions().add(cb.equal(root.get("publisherId"), userReviews.getPublisherId()));
					}
					if(StringUtil.isNotEmpty(userReviews.getReviewsContent())){
						predicate.getExpressions().add(cb.like(root.<String>get("reviewsContent"), "%"+userReviews.getReviewsContent().trim()+"%"));
					}
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	public void save(UserReviews userReviews) {
		userReviewsRepositroy.save(userReviews);

	}

	@Override
	public void delete(Integer id) {
		userReviewsRepositroy.delete(id);
	}

	@Override
	public UserReviews getId(Integer id) {
		// TODO Auto-generated method stub
		return userReviewsRepositroy.findOne(id);
	}

	@Override
	public void deleteUserReviews(Integer resourceId, String largeCategory) {
		// TODO Auto-generated method stub
		userReviewsRepositroy.deleteUserReviews(resourceId, largeCategory);
	}

}
