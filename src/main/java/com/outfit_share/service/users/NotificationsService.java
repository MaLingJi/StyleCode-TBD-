package com.outfit_share.service.users;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.users.Notifications;
import com.outfit_share.repository.users.NotificationsRepository;

@Service
public class NotificationsService {

    @Autowired
    private NotificationsRepository notiRepo;

    public List<Notifications> findByUserId(Integer userId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdTime");
        return notiRepo.findByUserDetailId(userId, sort);
    }

    public Notifications saveNotifications(Notifications notifications) {
        return notiRepo.save(notifications);
    }

    public Notifications updateStatus(Integer id, Integer status) {
        Optional<Notifications> optional = notiRepo.findById(id);
        if (optional.isPresent()) {
            Notifications notifications = optional.get();
            notifications.setStatus(status);
            return notiRepo.save(notifications);
        }
        return null;
    }
}
