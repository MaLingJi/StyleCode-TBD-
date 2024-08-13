package com.outfit_share.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.users.Notifications;

public interface NotificationsRepository extends JpaRepository<Notifications, Integer> {

}
