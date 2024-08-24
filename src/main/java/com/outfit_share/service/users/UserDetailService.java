package com.outfit_share.service.users;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.outfit_share.entity.users.UserDetail;
import com.outfit_share.entity.users.UserDetailDTO;
import com.outfit_share.entity.users.Users;
import com.outfit_share.repository.users.UserDetailRepository;

@Service
@Transactional
public class UserDetailService {

    @Autowired
    private UserDetailRepository udRepo;

    public UserDetailDTO findUserById(Integer userId) {
        UserDetail userDetail = udRepo.findById(userId).orElse(null);
        return converEntityToDto(userDetail);
    }

    public UserDetail findDetailById(Integer id) {
        Optional<UserDetail> optional = udRepo.findById(id);
        if (optional.isPresent()) {
            UserDetail dbUserDetail = optional.get();
            return dbUserDetail;
        }
        return null;
    }

    public UserDetail createDetail(Users users) {
        UserDetail uDetail = new UserDetail();
        uDetail.setUsers(users);
        uDetail.setUserName("user");
        uDetail.setCreatedTime(new Date());
        uDetail.setDiscountPoints(0);
        return udRepo.save(uDetail);
    }

    private UserDetailDTO converEntityToDto(UserDetail user) {
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setUserId(user.getId());
        userDetailDTO.setUserEmail(user.getUsers().getEmail());
        userDetailDTO.setRealName(user.getRealName());
        userDetailDTO.setUserName(user.getUserName());
        userDetailDTO.setAddress(user.getAddress());
        userDetailDTO.setPhone(user.getPhone());
        userDetailDTO.setCreatedTime(user.getCreatedTime());
        userDetailDTO.setUpdatedTime(user.getUpdatedTime());
        userDetailDTO.setUserPhoto(user.getUserPhoto());
        userDetailDTO.setDiscountPoints(user.getDiscountPoints());
        return userDetailDTO;
    }

    public UserDetail saveDetail(UserDetail uDetail) {
        return udRepo.save(uDetail);
    }
}
