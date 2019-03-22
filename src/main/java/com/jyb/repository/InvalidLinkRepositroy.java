package com.jyb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jyb.entity.InvalidLink;

public interface InvalidLinkRepositroy   extends JpaRepository< InvalidLink, Integer>,JpaSpecificationExecutor<InvalidLink>{
	@Query(value="delete from invalid_link where resource_id=?1 and large_category=?2",nativeQuery=true)
	@Modifying
	public void deleteInvalidLink(Integer resourceId,String largeCategory);
}
