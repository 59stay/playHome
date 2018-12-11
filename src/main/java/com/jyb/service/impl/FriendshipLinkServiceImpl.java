package com.jyb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jyb.entity.FriendshipLink;
import com.jyb.repository.FriendshipLinkRepository;
import com.jyb.service.FriendshipLinkService;

@Service("friendshipLinkService")
public class FriendshipLinkServiceImpl implements FriendshipLinkService{
 
	@Autowired
	private FriendshipLinkRepository  friendshipLinkRepository;
	
	@Override
	public List<FriendshipLink> listAll(Direction direction, String... properties) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(direction,properties);
		return friendshipLinkRepository.findAll(sort);
	}

}
