package com.outfit_share.service.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.posts.Users;
import com.outfit_share.repository.posts.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepo;
	//暫時處理id為空的狀態 進行CRUD
	public Users findUsersById(Integer userId) {
	    if (userId == null) {
	        throw new IllegalArgumentException("ID must not be null");
	    }
	    return usersRepo.findById(userId).orElse(null);//處理 Optional
	}
//	public Users findUsersById(Integer userId) {
//		 return usersRepo.findById(userId).orElse(null);//處理 Optional
//	}
	
	public boolean authenticateUser(Integer id, String email, String passwd) {
        Users user = usersRepo.findById(id).orElse(null);
        if (user != null && user.getUserEmail().equals(email) && user.getUserPassword().equals(passwd)) {
            return true;
        }
        return false;
    }
}
