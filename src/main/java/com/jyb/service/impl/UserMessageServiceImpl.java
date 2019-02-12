package com.jyb.service.impl;

import java.util.Date;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jyb.entity.GameInformation;
import com.jyb.entity.UserMessage;
import com.jyb.repository.UserMessageRepository;
import com.jyb.service.UserMessageService;

@Service("userMessageService")
@Transactional
public class UserMessageServiceImpl implements UserMessageService{
 
	@Autowired
	private UserMessageRepository userMessageRepository;
	

	@Override
	public List<UserMessage> listPage(UserMessage userMessage,Integer page, Integer pageSize, Direction direction, String... properties) {
		// TODO Auto-generated method stub
	/*	Pageable pageable = new PageRequest(page-1, pageSize, direction, properties);
		Page<UserMessage> pageUserMessage = userMessageRepository.findAll(pageable); 
		return pageUserMessage.getContent();*/
		Pageable pageable = new PageRequest(page-1, pageSize, direction, properties);
		Page<UserMessage> pageUserMessage = userMessageRepository.findAll(new Specification<UserMessage>() {
			@Override
			public Predicate toPredicate(Root<UserMessage> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate =  cb.conjunction();
				if(userMessage!=null){
				}
				return predicate;
			}
		},pageable);
		return pageUserMessage.getContent();
	}

	@Override
	public Long getCount(UserMessage userMessage) {
		// TODO Auto-generated method stub
		return userMessageRepository.count();
	}
	

	@Override
	public void save(UserMessage userMessage) {
		// TODO Auto-generated method stub
		userMessageRepository.save(userMessage);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		userMessageRepository.delete(id);
	}

	@Override
	public Integer getByDate(Integer userId, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return userMessageRepository.getByDate(userId, startDate, endDate);
	}

	

}
