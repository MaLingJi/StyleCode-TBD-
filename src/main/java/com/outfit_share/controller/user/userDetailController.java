package com.outfit_share.controller.user;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.outfit_share.entity.users.UserDetail;
import com.outfit_share.service.users.UserDetailService;
import com.outfit_share.util.JsonWebTokenUtility;

public class userDetailController {

    @Autowired
    private JsonWebTokenUtility jwtUtil;

    @Autowired
    private UserDetailService uDetailService;

    @GetMapping("/member/profile/{id}")
    public String showDetail(@PathVariable("id") Integer userId, @RequestHeader("Authorization") String token)
            throws JsonProcessingException {
        JSONObject responseJson = new JSONObject();

        Integer tokenUserId = jwtUtil.getUserIdFromToken(token);
        if (!tokenUserId.equals(userId)) {
            responseJson.put("success", false);
            responseJson.put("message", "沒有權限");
            return responseJson.toString();
        }

        UserDetail dbUserDetail = uDetailService.findDetailById(userId);

        ObjectMapper mapper = new ObjectMapper();
        JSONObject uDetailJson = mapper.convertValue(dbUserDetail, JSONObject.class);

        responseJson.put("userDetail", uDetailJson);

        return responseJson.toString();
    }

    @PostMapping("/member/profile/{id}")
    public String createProfile(@PathVariable("id") Integer userId, @RequestHeader("Authorization") String token,
            @RequestBody UserDetail uProfile) {
        JSONObject responseJson = new JSONObject();

        Integer tokenUserId = jwtUtil.getUserIdFromToken(token);
        if (!tokenUserId.equals(userId)) {
            responseJson.put("success", false);
            responseJson.put("message", "沒有權限");
            return responseJson.toString();
        }

        try {
            UserDetail existingProfile = uDetailService.findDetailById(userId);
            if (existingProfile != null) {
                // 如果存在就更新
            }
            return responseJson.toString();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return responseJson.toString();
    }
}
