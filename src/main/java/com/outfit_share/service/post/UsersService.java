package com.outfit_share.service.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.post.Users;
import com.outfit_share.repository.post.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepo;
	
	public Users findUsersById(Integer userId) {
		 return usersRepo.findById(userId).orElse(null);//處理 Optional
	}
}
