package com.outfit_share.service.users;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.outfit_share.entity.users.UserDetail;
import com.outfit_share.repository.users.UserDetailRepository;

@Service
@Transactional
public class UserDetailService {

    @Autowired
    private UserDetailRepository udRepo;

    public UserDetail findUserById(Integer userId) {
        return udRepo.findById(userId).orElse(null);
    }

    public UserDetail findDetailById(Integer id) {
        Optional<UserDetail> optional = udRepo.findById(id);
        if (optional.isPresent()) {
            UserDetail dbUserDetail = optional.get();
            return dbUserDetail;
        }
        return null;
    }

    public UserDetail saveDetail(UserDetail uDetail) {
        return udRepo.save(uDetail);
    }
}
