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

import com.jyb.entity.UserReviews;
import com.jyb.repository.UserReviewsRepositroy;
import com.jyb.service.UserReviewsService;

@Service("userReviewsService")
public class UserReviewsServiceImpl implements UserReviewsService {

	@Autowired
	private UserReviewsRepositroy userReviewsRepositroy;

	@Override
	public List<UserReviews> listPage(UserReviews userReviews, Integer page, Integer pageSize, Direction direction,
			String... properties) {
		// TODO Auto-generated method stub
		Pageable pageable = new PageRequest(page - 1, pageSize, direction, properties);
		Page<UserReviews> pageComment = userReviewsRepositroy.findAll(new Specification<UserReviews>() {

			@Override
			public Predicate toPredicate(Root<UserReviews> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (userReviews != null) {
					if (userReviews.getUserInformation() != null
							&& userReviews.getUserInformation().getId() != null) {
						predicate.getExpressions().add(cb.equal(root.get("userInformation").get("userId"),
								userReviews.getUserInformation().getId()));
					}
					if (userReviews.getLargeCategory() != null && userReviews.getResourceId() != null) {
						predicate.getExpressions()
								.add(cb.equal(root.get("largeCategory"), userReviews.getLargeCategory()));
						predicate.getExpressions().add(cb.equal(root.get("resourceId"), userReviews.getResourceId()));
					}
				}
				return predicate;
			}
		}, pageable);
		return pageComment.getContent();
	}

	@Override
	public Long getCount(UserReviews userReviews) {
		// TODO Auto-generated method stub
		Long count = userReviewsRepositroy.count(new Specification<UserReviews>() {

			@Override
			public Predicate toPredicate(Root<UserReviews> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (userReviews != null) {
					if (userReviews.getUserInformation() != null
							&& userReviews.getUserInformation().getId() != null) {
						predicate.getExpressions().add(cb.equal(root.get("userInformation").get("userId"),
								userReviews.getUserInformation().getId()));
					}
					if (userReviews.getLargeCategory() != null && userReviews.getResourceId() != null) {
						predicate.getExpressions()
								.add(cb.equal(root.get("largeCategory"), userReviews.getLargeCategory()));
						predicate.getExpressions().add(cb.equal(root.get("resourceId"), userReviews.getResourceId()));
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

}
