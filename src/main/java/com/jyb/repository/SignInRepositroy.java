package com.jyb.repository;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jyb.entity.SignIn;

public interface SignInRepositroy   extends JpaRepository< SignIn, Integer>,JpaSpecificationExecutor<SignIn>{
    /**
     * 根据userid查已签到的用户
     * @param userId
     * @param resourceId
     * @param largeCategory
     * @return
     */
	@Query(value="select * from sign_in where user_id =?1 and sign_in_time like CONCAT(?2,'%')",nativeQuery=true)
	public SignIn  getUserId(Integer userId,String signInTime);
	
}
