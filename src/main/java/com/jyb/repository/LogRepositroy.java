package com.jyb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jyb.entity.Log;

public interface LogRepositroy  extends JpaRepository<Log, Integer>,JpaSpecificationExecutor<Log>{

}
