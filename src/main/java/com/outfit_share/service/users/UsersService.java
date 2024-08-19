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

    public boolean checkEmail(String email) {
        Optional<Users> dbUser = uRepo.findByEmail(email);
        if (dbUser.isPresent()) {
            return true;
        }
        return false;
    }

    public Users resgister(Users users) {
        return uRepo.save(users);
    }

    public Users login(String loginUseremail, String loginPwd) {
        Optional<Users> optional = uRepo.findByEmail(loginUseremail);

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

}
