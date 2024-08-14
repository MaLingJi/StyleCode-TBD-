package com.outfit_share.util;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.Password;
import jakarta.annotation.PostConstruct;

@Component
public class JsonWebTokenUtility {

    @Value("${jwt.token.expire}")
    private long expire;

    private byte[] base64EncodedSecret;
    private char[] charArraySecret;

    @PostConstruct
    public void init() {

        // TODO:資料庫抓取密鑰
        String secret = "ALSKDJFH9874312GZMXNCBV";

        base64EncodedSecret = Base64.getEncoder().encode(secret.getBytes());
        charArraySecret = new String(base64EncodedSecret).toCharArray();

    }

    public String createEncryptedToken(String data, Long lifespan) {
        Date now = new Date();

        if (lifespan == null) {
            lifespan = this.expire * 60 * 1000;
        }
        // 當前時間的毫秒值 //有效時間毫秒
        long end = System.currentTimeMillis() + lifespan;
        Date expiredate = new Date(end);// 轉換成Date形式

        Password password = Keys.password(charArraySecret);
        JwtBuilder builder = Jwts.builder()
                .subject(data) // JWT內容主體
                .issuedAt(now) // 建立時間
                .expiration(expiredate) // 過期時限
                .encryptWith(password,
                        Jwts.KEY.PBES2_HS512_A256KW, // 使用 PBES2_HS512_A256KW 算法進行密鑰加密。
                        Jwts.ENC.A256GCM); // 使用 A256GCM 算法進行內容加密。
        String token = builder.compact(); // 將所有之前設置的 JWT 訊息（如 subject、發行時間、過期時間等）編碼並壓縮成一個單一的字符串。
        return token;
    }

    // 驗證
    public String validateEncryptedToken(String token) {

        Password password = Keys.password(charArraySecret); // 建立密碼
        JwtParser parser = Jwts.parser()
                .decryptWith(password) // 解密
                .build();
        try {
            Claims payload = parser.parseEncryptedClaims(token).getPayload();

            String subject = payload.getSubject();
            return subject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
