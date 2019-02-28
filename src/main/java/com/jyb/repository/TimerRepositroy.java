package com.jyb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.jyb.entity.Timer;

public interface TimerRepositroy extends JpaRepository<Timer, Integer>,JpaSpecificationExecutor<Timer>{

}