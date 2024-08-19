package com.outfit_share.repository.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.users.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByEmail(String email);
}
