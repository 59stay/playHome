package com.jyb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jyb.entity.InvalidLink;

public interface InvalidLinkRepositroy   extends JpaRepository< InvalidLink, Integer>,JpaSpecificationExecutor<InvalidLink>{

}
