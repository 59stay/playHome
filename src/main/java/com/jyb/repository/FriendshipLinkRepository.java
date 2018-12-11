package com.jyb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jyb.entity.FriendshipLink;

public interface FriendshipLinkRepository extends JpaRepository<FriendshipLink,Integer>,JpaSpecificationExecutor<FriendshipLink>{

}
