package com.jyb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jyb.entity.UserReviews;

public interface UserReviewsRepositroy extends JpaRepository<UserReviews, Integer>,JpaSpecificationExecutor<UserReviews>{

}
