package com.outfit_share.controller.user;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.outfit_share.entity.users.UserDetail;
import com.outfit_share.entity.users.UserDetailDTO;
import com.outfit_share.service.users.UserDetailService;
import com.outfit_share.service.users.UsersService;
import com.outfit_share.util.DatetimeConverter;
import com.outfit_share.util.JsonWebTokenUtility;

@RestController
public class userDetailController {

    @Autowired
    private JsonWebTokenUtility jwtUtil;

    @Autowired
    private UserDetailService uDetailService;

    @Autowired
    private UsersService uService;

    @GetMapping("/member/profile/{id}")
    public String showDetail(@PathVariable("id") Integer userId, @RequestHeader("Authorization") String token)
            throws JsonProcessingException {
        JSONObject responseJson = new JSONObject();
        System.out.println(userId);
        // 驗證user
        if (!jwtUtil.isUser(userId, token)) {
            responseJson.put("success", false);
            responseJson.put("message", "沒有權限");
            return responseJson.toString();
        }

        UserDetailDTO dbUserDetail = uDetailService.findUserById(userId);

        String createTime = DatetimeConverter.toString(dbUserDetail.getCreatedTime(), "yyyy-MM-dd HH:mm:ss");
        String updateTime = DatetimeConverter.toString(dbUserDetail.getUpdatedTime(), "yyyy-MM-dd HH:mm:ss");
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(dbUserDetail);
        JSONObject userDetailJson = new JSONObject(jsonString);
        userDetailJson.put("createdTime", createTime);
        userDetailJson.put("updatedTime", updateTime);

        responseJson.put("success", true);
        responseJson.put("userDetail", userDetailJson);

        return responseJson.toString();
    }

    @PutMapping("/member/profile/{id}")
    public String createProfile(@PathVariable("id") Integer userId, @RequestHeader("Authorization") String token,
            @RequestBody String json) {
        JSONObject responseJson = new JSONObject();

        // 驗證user
        if (!jwtUtil.isUser(userId, token)) {
            responseJson.put("success", false);
            responseJson.put("message", "沒有權限");
            return responseJson.toString();
        }

        try {
            JSONObject userObj = new JSONObject(json);
            UserDetail existingProfile = uDetailService.findDetailById(userId);
            if (!userObj.isNull("realName")) {
                existingProfile.setRealName(userObj.getString("realName"));
            }
            if (!userObj.isNull("userName")) {
                existingProfile.setUserName(userObj.getString("userName"));
            }
            if (!userObj.isNull("address")) {
                existingProfile.setAddress(userObj.getString("address"));
            }
            if (!userObj.isNull("phone")) {
                existingProfile.setPhone(userObj.getString("phone"));
            }
            existingProfile.setUpdatedTime(new Date());

            uDetailService.saveDetail(existingProfile);

            responseJson.put("success", true);
            responseJson.put("message", "更新成功");

        } catch (Exception e) {
            responseJson.put("success", true);
            responseJson.put("message", "操作失敗" + e.getMessage());

        }
        return responseJson.toString();
    }

    @PutMapping("/member/profile/photo/{id}")
    public String photoUpload(@PathVariable("id") Integer userId, @RequestHeader("Authorization") String token,
            @RequestParam("myPhoto") MultipartFile multipartFile)
            throws IllegalStateException, IOException {
        JSONObject responseJson = new JSONObject();
        // 驗證user
        if (!jwtUtil.isUser(userId, token)) {
            responseJson.put("success", false);
            responseJson.put("message", "沒有權限");
            return responseJson.toString();
        }
        UserDetail updateUserImage = uDetailService.updateUserImage(userId, multipartFile);
        responseJson.put("success", true);
        responseJson.put("newPhotoName", updateUserImage.getUserPhoto());
        responseJson.put("message", "更新成功");
        return responseJson.toString();
    }

    @GetMapping("/admin/todayregistrations")
    public ResponseEntity<Map<String, Object>> getSummary() {
        Map<String, Object> response = new HashMap<>();

        try {
            long totalUsers = uService.countUsers();
            long todayRegistrationCount = uDetailService.getTodayRegistrationCount();

            response.put("totalUsers", totalUsers);
            response.put("todayRegistrations", todayRegistrationCount);
            System.out.println("今日人數??");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
