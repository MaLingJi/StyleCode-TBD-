package com.outfit_share.service.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.outfit_share.entity.users.UserDetailDTO;
import com.outfit_share.entity.users.Users;
import com.outfit_share.repository.users.UsersRepository;

@Service
@Transactional
public class UsersService {

    @Autowired
    private PasswordEncoder pwdEncoder;

    @Autowired
    private UsersRepository uRepo;

    public Users findUserById(Integer id) {
        Optional<Users> optional = uRepo.findById(id);
        if (optional.isPresent()) {
            Users dbUser = optional.get();
            return dbUser;
        }
        return null;
    }

    public Page<UserDetailDTO> findAll(Specification<Users> spec, Pageable pageable) {

        Page<Users> userPage = uRepo.findAll(spec, pageable);
        // List<Users> userList = uRepo.findAll();
        // List<UserDetailDTO> userDTOList = new ArrayList<>();
        // if (userList != null && !userList.isEmpty()) {
        // for (Users user : userList) {
        // UserDetailDTO userDTO = converEntityToDto(user);
        // userDTOList.add(userDTO);
        // }
        // }
        return userPage.map(this::converEntityToDto);
    }

    public boolean checkEmail(String email) {
        Optional<Users> dbUser = uRepo.findByEmail(email);
        if (dbUser.isPresent()) {
            return true;
        }
        return false;
    }

    public Users findByEmail(String email) {
        Optional<Users> optional = uRepo.findByEmail(email);
        if (optional.isPresent()) {
            Users dbuser = optional.get();
            return dbuser;
        }
        return null;
    }

    public Users creatUsers(String userEmail, String userPwd) {

        // 將密碼加密
        String encodedPwd = pwdEncoder.encode(userPwd);
        Users users = new Users();
        users.setEmail(userEmail);
        users.setPwd(encodedPwd);
        users.setPermissions("Member");
        return uRepo.save(users);
    }

    public Users googleCreateUser(String userEmail) {
        Users user = new Users();
        user.setEmail(userEmail);
        user.setPermissions("Member");

        return uRepo.save(user);
    }

    public Users login(String loginUserEmail, String loginPwd) {
        Optional<Users> optional = uRepo.findByEmail(loginUserEmail);

        if (optional.isPresent()) {
            Users dbUser = optional.get();
            String dbPwd = dbUser.getPwd();
            boolean result = pwdEncoder.matches(loginPwd, dbPwd);

            if (result) {
                return dbUser;
            }
        }
        return null;
    }

    public boolean changePassword(Integer userId, String oldPwd, String newPwd) {
        Optional<Users> optional = uRepo.findById(userId);
        if (optional.isPresent()) {
            Users dbUser = optional.get();
            String dbPwd = dbUser.getPwd();
            boolean result = pwdEncoder.matches(oldPwd, dbPwd);
            if (result) {
                String encodedPwd = pwdEncoder.encode(newPwd);
                dbUser.setPwd(encodedPwd);
                return true;
            }
        }
        return false;
    }

    public boolean updateUserPermissions(Integer id, String newRole) {
        Users user = findUserById(id);
        if (user != null) {
            user.setPermissions(newRole);
            uRepo.save(user);
            return true;
        }
        return false;
    }

    public long countUsers() {
        return uRepo.count();
    }

    private UserDetailDTO converEntityToDto(Users user) {
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setUserId(user.getId());
        userDetailDTO.setUserEmail(user.getEmail());
        userDetailDTO.setPermissions(user.getPermissions());
        userDetailDTO.setRealName(user.getUserDetail().getRealName());
        userDetailDTO.setUserName(user.getUserDetail().getUserName());
        userDetailDTO.setAddress(user.getUserDetail().getAddress());
        userDetailDTO.setPhone(user.getUserDetail().getPhone());
        userDetailDTO.setCreatedTime(user.getUserDetail().getCreatedTime());
        userDetailDTO.setUpdatedTime(user.getUserDetail().getUpdatedTime());
        userDetailDTO.setUserPhoto(user.getUserDetail().getUserPhoto());
        userDetailDTO.setDiscountPoints(user.getUserDetail().getDiscountPoints());
        return userDetailDTO;
    }
}
