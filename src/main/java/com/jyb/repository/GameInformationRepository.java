package com.jyb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jyb.entity.GameInformation;

public interface GameInformationRepository  extends JpaRepository<GameInformation, Integer>,JpaSpecificationExecutor<GameInformation>{

}
