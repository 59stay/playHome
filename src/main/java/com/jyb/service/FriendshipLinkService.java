package com.jyb.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.jyb.entity.FriendshipLink;

public interface FriendshipLinkService {
 /**
  * 查询所有的友情链接
  * @param direction
  * @param properties
  * @return
  */
 public List<FriendshipLink> listAll(Direction direction,String...properties);
 
}
