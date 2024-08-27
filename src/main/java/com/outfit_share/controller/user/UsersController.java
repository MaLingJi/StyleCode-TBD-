package com.outfit_share.controller.user;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.outfit_share.entity.users.Users;
import com.outfit_share.service.users.UserDetailService;
import com.outfit_share.service.users.UsersService;
import com.outfit_share.util.JsonWebTokenUtility;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UsersController {

    @Autowired
    private UsersService uService;

    @Autowired
    private UserDetailService uDetailService;

    @Autowired
    private JsonWebTokenUtility jwtUtil;

    // 確認此Email有沒有被註冊過
    @PostMapping("/checkEmail")
    public ResponseEntity<String> checkUser(@RequestBody String json) {

        JSONObject reqJsonObj = new JSONObject(json);
        String userEmail = reqJsonObj.isNull("userEmail") ? null : reqJsonObj.getString("userEmail");
        boolean status = uService.checkEmail(userEmail);
        if (status) {
            return new ResponseEntity<String>("Y", HttpStatus.OK);
        }
        return new ResponseEntity<String>("N", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> resgister(@RequestBody String json) {

        JSONObject reqJsonObj = new JSONObject(json);
        String userEmail = reqJsonObj.isNull("userEmail") ? null : reqJsonObj.getString("userEmail");
        String userPwd = reqJsonObj.isNull("userPwd") ? null : reqJsonObj.getString("userPwd");
        Users users = uService.creatUsers(userEmail, userPwd);

        uDetailService.createDetail(users);

        return ResponseEntity.ok("註冊成功");
    }

    @PostMapping("/login")
    public String login(@RequestBody String json) throws JsonProcessingException {
        JSONObject responseJson = new JSONObject();

        JSONObject reqJsonObj = new JSONObject(json);
        String userEmail = reqJsonObj.isNull("userEmail") ? null : reqJsonObj.getString("userEmail");
        String userPwd = reqJsonObj.isNull("userPwd") ? null : reqJsonObj.getString("userPwd");

        if (userEmail == null || userEmail.length() == 0 || userPwd == null || userPwd.length() == 0) {
            responseJson.put("success", false);
            responseJson.put("message", "請輸入帳號密碼");
            return responseJson.toString();
        }

        Users dbUser = uService.login(userEmail, userPwd);

        if (dbUser == null) {
            responseJson.put("success", false);
            responseJson.put("message", "帳號或密碼錯誤");
        } else {
            responseJson.put("success", true);
            responseJson.put("message", "登入成功");

            JSONObject user = new JSONObject()
                    .put("userId", dbUser.getId())
                    .put("permissions", dbUser.getPermissions());

            String token = jwtUtil.createEncryptedToken(user.toString(), null);
            // TODO:待確認要存甚麼資訊
            responseJson.put("token", token);
            responseJson.put("userId", dbUser.getId());
            responseJson.put("permissions", dbUser.getPermissions());
        }

        return responseJson.toString();
    }
}
