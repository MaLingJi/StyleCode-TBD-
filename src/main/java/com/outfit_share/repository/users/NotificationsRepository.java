package com.outfit_share.repository.users;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.users.Notifications;
import java.util.List;

public interface NotificationsRepository extends JpaRepository<Notifications, Integer> {
    List<Notifications> findByUserDetailId(Integer userId, Sort sort);
}
