package com.jyb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.jyb.entity.Software;

public interface SoftwareRepositroy  extends JpaRepository<Software, Integer>,JpaSpecificationExecutor<Software>{

}
