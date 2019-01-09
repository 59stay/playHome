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
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jyb.entity.GameInformation;
import com.jyb.entity.UserMessage;
import com.jyb.repository.UserMessageRepository;
import com.jyb.service.UserMessageService;

@Service("userMessageService")
public class UserMessageServiceImpl implements UserMessageService{
 
	@Autowired
	private UserMessageRepository userMessageRepository;
	

	@Override
	public List<UserMessage> listPage(Integer page, Integer pageSize, Direction direction, String... properties) {
		// TODO Auto-generated method stub
		Pageable pageable = new PageRequest(page-1, pageSize, direction, properties);
		Page<UserMessage> pageUserMessage = userMessageRepository.findAll(pageable); 
		return pageUserMessage.getContent();
	}

	@Override
	public Long getCount() {
		// TODO Auto-generated method stub
		return userMessageRepository.count();
	}
	

	@Override
	public void save(UserMessage userMessage) {
		// TODO Auto-generated method stub
		userMessageRepository.save(userMessage);
	}

	

}
