package com.outfit_share.service.users;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.users.UserDetail;
import com.outfit_share.repository.users.UserDetailRepository;

@Service
public class UserDetailService {

    @Autowired
    private UserDetailRepository udRepo;

    public UserDetail findDetailById(Integer id) {
        Optional<UserDetail> optional = udRepo.findById(id);
        if (optional.isPresent()) {
            UserDetail dbUserDetail = optional.get();
            return dbUserDetail;
        }
        return null;
    }
}
