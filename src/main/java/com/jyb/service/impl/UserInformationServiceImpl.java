package com.jyb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyb.entity.UserInformation;
import com.jyb.repository.UserInformationRepositroy;
import com.jyb.service.UserInformationService;

@Service("userInformationService")
public class UserInformationServiceImpl implements UserInformationService {
	@Autowired
	private UserInformationRepositroy userInformationRepositroy;

	@Override
	public void save(UserInformation userInformation) {
		// TODO Auto-generated method stub
		userInformationRepositroy.save(userInformation);
	}

	@Override
	public UserInformation findByUserName(String userName) {
		// TODO Auto-generated method stub
		return userInformationRepositroy.findByUserName(userName);
	}

	@Override
	public UserInformation findByEmail(String email) {
		// TODO Auto-generated method stub
		return userInformationRepositroy.findByEmail(email);
	}

	
	
}
