package com.outfit_share.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.outfit_share.entity.users.UserDetail;
import com.outfit_share.entity.users.UserDetailDTO;
import com.outfit_share.entity.users.Users;
import com.outfit_share.service.users.UserDetailService;
import com.outfit_share.service.users.UsersService;
import com.outfit_share.util.JsonWebTokenUtility;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        // if (userEmail == null || userEmail.length() == 0 || userPwd == null ||
        // userPwd.length() == 0) {
        // responseJson.put("success", false);
        // responseJson.put("message", "請輸入帳號密碼");
        // return responseJson.toString();
        // }

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

    @PutMapping("changePassword/{userId}")
    public String putMethodName(@PathVariable Integer userId, @RequestBody String json,
            @RequestHeader("Authorization") String token) {
        JSONObject responseJson = new JSONObject();
        JSONObject reqJsonObj = new JSONObject(json);
        // 驗證user
        if (!jwtUtil.isUser(userId, token)) {
            responseJson.put("success", false);
            responseJson.put("message", "沒有權限");
            return responseJson.toString();
        }
        String oldPwd = reqJsonObj.isNull("oldPwd") ? null : reqJsonObj.getString("oldPwd");
        String newPwd = reqJsonObj.isNull("newPwd") ? null : reqJsonObj.getString("newPwd");

        boolean result = uService.changePassword(userId, oldPwd, newPwd);
        if (result) {
            responseJson.put("success", true);
            responseJson.put("message", "更新成功");
        } else {
            responseJson.put("success", false);
            responseJson.put("message", "更新失敗");
        }
        return responseJson.toString();
    }

    ////// 後台相關
    @GetMapping("/admin/userback")
    public ResponseEntity<Map<String, Object>> getAllusers(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(defaultValue = "all") String permissions, @RequestParam(required = false) String search) {

        Sort sort = Sort.by(sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
                "userDetail.createdTime");
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Specification<Users> spec = (root, query, criteriabuilder) -> {
            // 連接到 UserDetail
            Join<Users, UserDetail> userDetailJoin = root.join("userDetail");

            // 創建過濾條件
            Predicate predicate = criteriabuilder.conjunction();
            if (!"all".equalsIgnoreCase(permissions)) {
                predicate = criteriabuilder.and(predicate, criteriabuilder.equal(root.get("permissions"), permissions));
            }
            System.out.println(search);
            if (search != null && !search.isEmpty()) {
                String likePattern = "%" + search.trim().toLowerCase() + "%";
                Predicate emailPredicate = criteriabuilder.like(criteriabuilder.lower(root.get("email")), likePattern);
                predicate = criteriabuilder.and(predicate, emailPredicate);
            }
            query.where(predicate);
            return predicate;
        };

        Page<UserDetailDTO> userPage = uService.findAll(spec, pageable);

        Map<String, Object> userResponse = new HashMap<>();
        userResponse.put("items", userPage.getContent());
        userResponse.put("currentPage", userPage.getNumber());
        userResponse.put("totalItems", userPage.getTotalElements());
        userResponse.put("totalPages", userPage.getTotalPages());

        if (userPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userResponse, HttpStatus.OK);

        // if (users.isEmpty()) {
        // return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        // }
        // return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/admin/userback/{id}")
    public ResponseEntity<String> changeRole(@PathVariable Integer id, @RequestBody String json) {

        JSONObject reqJsonObj = new JSONObject(json);
        String newRole = reqJsonObj.getString("role");
        boolean updated = uService.updateUserPermissions(id, newRole);

        if (updated) {
            return new ResponseEntity<>("權限更新成功", HttpStatus.OK);

        } else {

            return new ResponseEntity<>("沒有找到使用者", HttpStatus.NOT_FOUND);
        }
    }

    // Google 登入後請求資料
    @GetMapping("/user-info")
    public Map<String, Object> getUserInfo(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String userInfo = null;
        Map<String, Object> response = new HashMap<>();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("auth_info".equals(cookie.getName())) {
                    userInfo = cookie.getValue();
                    break;
                }
            }
        }

        if (userInfo != null) {
            String[] parts = userInfo.split("\\|");
            String token = parts[0];
            Integer userId = Integer.parseInt(parts[1]);
            String permissions = parts[2];

            response.put("token", token);
            response.put("userId", userId);
            response.put("permissions", permissions);
        }

        return response;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        // 清除 cookie
        Cookie cookie = new Cookie("auth_info", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 設置cookie 過期時間為0
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }
}
