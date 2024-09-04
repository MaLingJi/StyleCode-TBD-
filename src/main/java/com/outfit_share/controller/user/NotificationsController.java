package com.outfit_share.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;

import com.outfit_share.entity.users.Notifications;
import com.outfit_share.service.users.NotificationsService;
import com.outfit_share.util.JsonWebTokenUtility;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class NotificationsController {

    @Autowired
    private NotificationsService notiService;

    @Autowired
    private JsonWebTokenUtility jwtUtil;

    @GetMapping("/member/notifications/{userId}")
    public String getNotificationsList(@PathVariable("userId") Integer userId,
            @RequestHeader("Authorization") String token)
            throws JSONException {
        JSONObject responseJson = new JSONObject();

        // 驗證user
        if (!jwtUtil.isUser(userId, token)) {
            responseJson.put("success", false);
            responseJson.put("message", "沒有權限");
            return responseJson.toString();
        }

        List<Notifications> dbNotifications = notiService.findByUserId(userId);
        JSONArray array = new JSONArray();
        int unreadCount = 0;
        if (dbNotifications != null && !dbNotifications.isEmpty()) {
            for (Notifications notification : dbNotifications) {
                JSONObject notificationJson = new JSONObject()
                        .put("Nid", notification.getId())
                        .put("message", notification.getMessage())
                        .put("type", notification.getType())
                        .put("status", notification.getStatus())
                        .put("createdTime", notification.getCreatedTime())
                        .put("userId", notification.getUserDetail().getId());

                array.put(notificationJson);
                if (notification.getStatus() == 0) {
                    unreadCount++;
                }
            }
            responseJson.put("success", true);
            responseJson.put("notificationList", array);
            responseJson.put("unreadCount", unreadCount);
        } else {
            responseJson.put("success", true);
            responseJson.put("notificationList", null);
        }
        return responseJson.toString();
    }

    @PutMapping("/member/notifications/{id}")
    public String changeStatus(@PathVariable Integer id) throws JSONException {
        JSONObject responseJson = new JSONObject();

        try {
            Notifications updatedNoti = notiService.updateStatus(id, 1);
            responseJson.put("success", true);
            responseJson.put("notification", updatedNoti);
        } catch (RuntimeException e) {
            responseJson.put("success", false);
            responseJson.put("notification", null);

        }

        return responseJson.toString();
    }
}
