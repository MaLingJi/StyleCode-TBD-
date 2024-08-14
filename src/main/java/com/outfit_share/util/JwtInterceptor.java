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
        String method = request.getMethod();
        if (!"OPTIONS".equals(method)) {
            // 檢查是否有"已登入"(Header)資訊
            String auth = request.getHeader("Authorization");
            JSONObject user = authorizationHeader(auth);
            if (user == null || user.length() == 0) {
                // 沒有: 阻止使用者呼叫
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Headers", "*");
                return false;
            } else {
                // 有
                // 檢查使用者有無權限
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
