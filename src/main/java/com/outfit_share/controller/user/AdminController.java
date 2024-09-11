package com.outfit_share.controller.user;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.outfit_share.util.JsonWebTokenUtility;

@RestController
public class AdminController {

    @Autowired
    private JsonWebTokenUtility jwtUtil;

    @GetMapping("/admin")
    public ResponseEntity<?> verifyAdminStatus(@RequestHeader("Authorization") String authHeader) {
        try {
            // 驗證 JWT
            String token = authHeader.substring(7); // 去掉 "Bearer " 前綴
            String userData = jwtUtil.validateEncryptedToken(token);
            
            if (userData == null) {
                return ResponseEntity.status(401).body("Invalid token");
            }

            JSONObject userDataJson = new JSONObject(userData);
            String permissions = userDataJson.optString("permissions");

            if ("Admin".equals(permissions)) {
                return ResponseEntity.ok().body(new JSONObject().put("isAdmin", true).toString());
            } else {
                return ResponseEntity.status(403).body("User is not an admin");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error verifying admin status");
        }
    }
}