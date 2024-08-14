package com.outfit_share.repository.posts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.posts.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {

}
