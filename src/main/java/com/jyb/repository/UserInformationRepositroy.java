package com.jyb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jyb.entity.UserInformation;
 
public interface UserInformationRepositroy extends JpaRepository<UserInformation, Integer>,JpaSpecificationExecutor<UserInformation>{
    /**
     * 根据用户名获取用户对象
     * @param userName
     * @return
     */
	@Query(value="select * from user_information where user_name=?1",nativeQuery=true)
	public UserInformation findByUserName(String userName);
    
    /**
     * 根据验证获取用户对象
     * @param email
     * @return
     */
    @Query(value="select * from user_information where email=?1",nativeQuery=true)
    public UserInformation findByEmail(String email);
    
}
