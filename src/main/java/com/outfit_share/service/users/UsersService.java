package com.outfit_share.service.users;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public boolean checkEmail(String email) {
        Optional<Users> dbUser = uRepo.findByEmail(email);
        if (dbUser.isPresent()) {
            return true;
        }
        return false;
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

    public boolean changePassword(String userEmail, String oldPwd, String newPwd) {
        Users dbUser = this.login(userEmail, oldPwd);
        if (dbUser != null && newPwd != null && newPwd.length() != 0) {
            String encodedPwd = pwdEncoder.encode(newPwd);
            dbUser.setPwd(encodedPwd);
            uRepo.save(dbUser);
            return true;
        }
        return false;
    }

}
