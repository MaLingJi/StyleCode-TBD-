	package com.outfit_share.util;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JsonWebTokenUtility jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String method = request.getMethod(); // 獲取HTTP請求方法(GET,POST,PUT,DELETE,OPTIONS...)
        if (!"OPTIONS".equals(method)) { // 若不是options方法則...
            // 檢查是否有"已登入"(Header)資訊
            String auth = request.getHeader("Authorization");
            JSONObject user = authorizationHeader(auth);
            if (user == null || user.length() == 0) {
                // 沒有: 阻止使用者呼叫
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 設置403狀態碼
                response.setHeader("Access-Control-Allow-Credentials", "true");// 允許跨域請求
                response.setHeader("Access-Control-Allow-Origin", "*"); // 指定那些網域可以請求 "*":要改成 "https://example.com"
                response.setHeader("Access-Control-Allow-Headers", "*"); // 指定實際請求中可以使用那些Header
                return false;
            } else {
                // 有
                // 檢查使用者有無權限
                String role = user.optString("permissionsauth");
                String requestURI = request.getRequestURI();

                if (requestURI.startsWith("/admin") && !"Admin".equals(role)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                } else if (requestURI.startsWith("/member") && !"Member".equals(role)) {
                }
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        }
        return true;
    }

    private JSONObject authorizationHeader(String auth) {

        if (auth != null && auth.length() != 0) {
            System.out.println("auth = " + auth);
            String token = auth.substring(7); // 去掉'Bearer '
            String data = jwtUtil.validateEncryptedToken(token);
            System.out.println("data = " + data);
            if (data != null && data.length() != 0) {
                return new JSONObject(data);
            }
        }
        return null;
    }
}
