package com.outfit_share.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.outfit_share.config.GoogleOauthConfig;
import com.outfit_share.entity.users.Users;
import com.outfit_share.service.users.UserDetailService;
import com.outfit_share.service.users.UsersService;
import com.outfit_share.util.JsonWebTokenUtility;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class GoogleOAuth2 {

    @Autowired
    private GoogleOauthConfig googleOauthConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UsersService uService;

    @Autowired
    private UserDetailService uDetailService;

    @Autowired
    private JsonWebTokenUtility jwtUtil;

    private final String scope = "https://www.googleapis.com/auth/userinfo.email";

    @GetMapping("/google-login")
    public String googleLogin() {
        String authURL = "https://accounts.google.com/o/oauth2/v2/auth?" +
                "client_id=" + googleOauthConfig.getClientId()
                + "&response_type=code" +
                "&scope=openid%20email%20profile" + "&redirect_uri=" + googleOauthConfig.getRedirectUri() +
                "&state=state";

        return "redirect:" + authURL;
    }

    @GetMapping("/google-callback")
    public String getMethodName(@RequestParam(required = false) String code, HttpServletResponse response)
            throws JsonMappingException, JsonProcessingException {

        if (code == null) {
            String authUri = "https://accounts.google.com/o/oauth2/v2/auth?response_type=code" +
                    "&client_id=" + googleOauthConfig.getClientId() +
                    "&redirect_uri=" + googleOauthConfig.getRedirectUri() +
                    "&scope=" + scope;
            return "redirect:" + authUri;
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("code", code);
            map.add("client_id", googleOauthConfig.getClientId());
            map.add("client_secret", googleOauthConfig.getClientSecret());
            map.add("redirect_uri", googleOauthConfig.getRedirectUri());
            map.add("grant_type", "authorization_code");

            HttpEntity<MultiValueMap<String, String>> requst = new HttpEntity<>(map, headers);

            ResponseEntity<String> googleResponse = restTemplate.postForEntity("https://oauth2.googleapis.com/token",
                    requst,
                    String.class);
            String credentials = googleResponse.getBody();
            System.out.println("credentials" + credentials);

            JsonNode jsonNode = new ObjectMapper().readTree(credentials);
            String accessToken = jsonNode.get("access_token").asText();
            String idToken = jsonNode.get("id_token").asText();

            System.out.println(accessToken);
            System.out.println("====================");
            System.out.println(idToken);
            HttpHeaders header2 = new HttpHeaders();
            header2.setBearerAuth(accessToken);

            HttpEntity<Object> entity = new HttpEntity<>(header2);
            ResponseEntity<String> googleResponse2 = restTemplate.exchange(
                    "https://www.googleapis.com/oauth2/v1/userinfo?alt=json", HttpMethod.GET, entity, String.class);

            String payloadResponse = googleResponse2.getBody();

            JsonNode payloadJsonNode = new ObjectMapper().readTree(payloadResponse);

            System.out.println("payloadJsonNode : " + payloadJsonNode);

            String googleId = payloadJsonNode.get("id").asText();
            String googleEmail = payloadJsonNode.get("email").asText();
            String googleName = payloadJsonNode.get("name").asText();
            String googlePhoto = payloadJsonNode.get("picture").asText();
            // String googleLocale = payloadJsonNode.get("locale").asText(); 使用者的地區&語言

            boolean hasEmail = uService.checkEmail(googleEmail);

            if (!hasEmail) {
                Users user = uService.googleCreateUser(googleEmail);

                uDetailService.googleCreate(user, googleName, googlePhoto);
            }

            Users dbUser = uService.findByEmail(googleEmail);

            JSONObject user = new JSONObject()
                    .put("userId", dbUser.getId())
                    .put("permissions", dbUser.getPermissions());

            String token = jwtUtil.createEncryptedToken(user.toString(), null);

            String authInfo = token + "|" + dbUser.getId() + "|" + dbUser.getPermissions();

            Cookie cookie = new Cookie("auth_info", authInfo);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:" + "https://stylecode.online/LoginSuccess";
        }
    }

}
