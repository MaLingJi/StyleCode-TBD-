package com.outfit_share.service.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.users.Notifications;
import com.outfit_share.repository.users.NotificationsRepository;

@Service
public class NotificationsService {

    @Autowired
    private NotificationsRepository notiRepo;

    public List<Notifications> findByUserId(Integer userId) {
        return notiRepo.findByUserDetailId(userId);
    }

    public Notifications saveNotifications(Notifications notifications) {
        return notiRepo.save(notifications);
    }
}
