package com.outfit_share.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.outfit_share.entity.users.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail, Integer> {

    @Query("SELECT COUNT(u) FROM UserDetail u WHERE CAST(u.createdTime AS DATE) = CAST(GETDATE() AS DATE)")
    long countTodayRegistrations();
}
