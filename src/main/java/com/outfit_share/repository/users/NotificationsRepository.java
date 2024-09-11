package com.outfit_share.repository.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.users.Notifications;
import java.util.List;

public interface NotificationsRepository extends JpaRepository<Notifications, Integer> {
    List<Notifications> findByUserDetailId(Integer userId, Sort sort);

    List<Notifications> findByUserDetailIdAndStatus(Integer userId, Integer status);

    Page<Notifications> findByUserDetailId(Integer userId, Pageable pageable);
}
