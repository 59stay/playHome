package com.jyb.repository;


import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jyb.entity.UserMessage;

public interface UserMessageRepository extends JpaRepository<UserMessage, Integer>,JpaSpecificationExecutor<UserMessage>{

	@Query(value="select count(id) from user_message where user_id=?1 and  message_creation_time  between ?2 and ?3",nativeQuery=true)
	public Integer getByDate(Integer userId,Date startDate,Date endDate);
}
