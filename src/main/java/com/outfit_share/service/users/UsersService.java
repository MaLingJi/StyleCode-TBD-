package com.outfit_share.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.users.Users;
import com.outfit_share.repository.users.UsersRepository;

@Service
public class UsersService {

    @Autowired
    private UsersRepository uRepo;

    public boolean checkEmail(String email) {

        Users dbUser = uRepo.findByEmail(email);

        if (dbUser != null) {
            return true;
        }
        return false;
    }

}
