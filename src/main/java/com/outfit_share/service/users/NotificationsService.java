package com.outfit_share.service.users;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.users.Notifications;
import com.outfit_share.entity.users.NotificationsDTO;
import com.outfit_share.repository.users.NotificationsRepository;

@Service
public class NotificationsService {

    @Autowired
    private NotificationsRepository notiRepo;

    public List<Notifications> findByUserId(Integer userId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdTime");
        return notiRepo.findByUserDetailId(userId, sort);
    }

    public Page<NotificationsDTO> findNotificationsByUserId(Integer userId, int page, int pageSize) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "createdTime")); // 創建 Pageable 對象

        Page<Notifications> pagedResult = notiRepo.findByUserDetailId(userId, paging); // 使用 Pageable 進行查詢

        return pagedResult.map(this::convertEntityToDto);
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

    public void allRead(Integer userId) {
        List<Notifications> unreadNotifications = notiRepo.findByUserDetailIdAndStatus(userId, 0);

        unreadNotifications.forEach(notification -> {
            notification.setStatus(1); // 1 表示已读
        });
        notiRepo.saveAll(unreadNotifications);
    }

    private NotificationsDTO convertEntityToDto(Notifications notification) {
        NotificationsDTO notificationsDTO = new NotificationsDTO();
        notificationsDTO.setId(notification.getId());
        notificationsDTO.setMessage(notification.getMessage());
        notificationsDTO.setStatus(notification.getStatus());
        notificationsDTO.setType(notification.getType());
        notificationsDTO.setCreatedTime(notification.getCreatedTime());
        notificationsDTO.setUserId(notification.getUserDetail().getId());
        return notificationsDTO;
    }
}
