package com.outfit_share.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.users.UserDetail;
import com.outfit_share.repository.users.UserDetailRepository;

@Service
public class UserDetailService {

	@Autowired
    private UserDetailRepository userDetailRepository;
	
	public UserDetail findUserById(Integer userId) {
		return userDetailRepository.findById(userId).orElse(null);
	}

}
