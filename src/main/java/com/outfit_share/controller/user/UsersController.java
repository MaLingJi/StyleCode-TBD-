package com.outfit_share.controller.user;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.outfit_share.entity.users.UserDetail;
import com.outfit_share.entity.users.Users;
import com.outfit_share.service.users.UserDetailService;
import com.outfit_share.service.users.UsersService;
import com.outfit_share.util.JsonWebTokenUtility;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UsersController {

    @Autowired
    private UsersService uService;

    @Autowired
    private UserDetailService uDetailService;

    @Autowired
    private JsonWebTokenUtility jwtUtil;

    @GetMapping("/login")
    public String getMethodName() {
        return "login";
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
            UserDetail dbUserDetail = uDetailService.findDetailById(dbUser.getId());

            ObjectMapper mapper = new ObjectMapper();
            String uDetailJson = mapper.writeValueAsString(dbUserDetail);

            responseJson.put("success", true);
            responseJson.put("message", "登入成功");

            JSONObject user = new JSONObject()
                    .put("userId", dbUser.getId());
            // .put("permissions", dbUserDetail.getPermissions());

            String token = jwtUtil.createEncryptedToken(user.toString(), null);
            responseJson.put("token", token);
            // TODO:待確認要存甚麼資訊
            responseJson.put("userEmail", dbUser.getEmail());
            responseJson.put("userDetail", uDetailJson);
        }

        return responseJson.toString();
    }

    @PostMapping("/register")
    public String postMethodName(@RequestParam("userEmail") String userEmail, @RequestParam("userPwd") String pwd) {

        uService.resgister(userEmail, pwd);

        return "login";
    }

}
