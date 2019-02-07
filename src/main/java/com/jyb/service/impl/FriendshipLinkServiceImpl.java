package com.jyb.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jyb.entity.FriendshipLink;
import com.jyb.repository.FriendshipLinkRepository;
import com.jyb.service.FriendshipLinkService;

@Service("friendshipLinkService")
@Transactional
public class FriendshipLinkServiceImpl implements FriendshipLinkService{
 
	@Autowired
	private FriendshipLinkRepository  friendshipLinkRepository;
	
	@Override
	public List<FriendshipLink> listAll(Direction direction, String... properties) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(direction,properties);
		return friendshipLinkRepository.findAll(sort);
	}


	@Override
	public void save(FriendshipLink friendshipLink) {
		// TODO Auto-generated method stub
		friendshipLinkRepository.save(friendshipLink);
	}

	@Override
	public FriendshipLink getById(Integer id) {
		// TODO Auto-generated method stub
		return friendshipLinkRepository.getOne(id);
	}


	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		friendshipLinkRepository.delete(id);
	}

}
