package com.jyb.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jyb.entity.FriendshipLink;
import com.jyb.entity.Timer;
import com.jyb.repository.FriendshipLinkRepository;
import com.jyb.repository.TimerRepositroy;
import com.jyb.service.TimerService;

@Service("timerService")
@Transactional
public class TimerServiceImpl implements TimerService{
	@Autowired
	private  TimerRepositroy timerRepositroy;
	
	
	@Override
	public List<Timer> listAll() {
		// TODO Auto-generated method stub
		return timerRepositroy.findAll();
	}

	@Override
	public void save(Timer timer) {
		// TODO Auto-generated method stub
		timerRepositroy.save(timer);
	}


	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		timerRepositroy.delete(id);
	}

	@Override
	public Timer getId(Integer id) {
		// TODO Auto-generated method stub
		return timerRepositroy.getOne(id);
	}
	
}
