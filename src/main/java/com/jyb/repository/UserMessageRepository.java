package com.jyb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jyb.entity.UserMessage;

public interface UserMessageRepository extends JpaRepository<UserMessage, Integer>,JpaSpecificationExecutor<UserMessage>{

	
}
