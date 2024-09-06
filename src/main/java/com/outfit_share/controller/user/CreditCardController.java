package com.outfit_share.controller.user;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.configurationprocessor.json.JSONArray;
// import org.springframework.boot.configurationprocessor.json.JSONException;
// import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;

import com.outfit_share.entity.users.CreditCards;
import com.outfit_share.service.users.CreditCardService;
import com.outfit_share.util.JsonWebTokenUtility;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class CreditCardController {

    @Autowired
    private CreditCardService cardService;

    @Autowired
    private JsonWebTokenUtility jwtUtil;

    @GetMapping("/member/creditCard/{userId}")
    public String getCreditCard(@PathVariable("userId") Integer userId,
            @RequestHeader("Authorization") String token) throws JSONException {

        JSONObject responseJson = new JSONObject();

        // 驗證user
        if (!jwtUtil.isUser(userId, token)) {
            responseJson.put("success", false);
            responseJson.put("message", "沒有權限");
            return responseJson.toString();
        }

        List<CreditCards> cards = cardService.findCards(userId);
        JSONArray array = new JSONArray();
        if (cards != null && !cards.isEmpty()) {
            for (CreditCards card : cards) {
                JSONObject cardJson = new JSONObject()
                        .put("cardId", card.getCardId())
                        .put("cardNumber", card.getCardNumber());
                array.put(cardJson);
            }
            responseJson.put("success", true);
            responseJson.put("cardList", array);
        } else {
            responseJson.put("success", true);
            responseJson.put("cardList", array);
        }
        return responseJson.toString();
    }

    @PostMapping("/member/creditCard/{userId}")
    public String creatCard(@PathVariable Integer userId, @RequestBody String json,
            @RequestHeader("Authorization") String token) throws JSONException {
        JSONObject responseJson = new JSONObject();
        JSONObject reqJsonObj = new JSONObject(json);
        // 驗證user
        if (!jwtUtil.isUser(userId, token)) {
            responseJson.put("success", false);
            responseJson.put("message", "沒有權限");
            return responseJson.toString();
        }
        try {
            cardService.saveCard(userId, reqJsonObj.getString("cardNumber"), reqJsonObj.getString("expirationDate"),
                    reqJsonObj.getString("securityCode"), reqJsonObj.getString("holderName"),
                    reqJsonObj.getString("billingAddress"));

            responseJson.put("success", true);
            responseJson.put("message", "信用卡新增成功");

        } catch (ParseException e) {
            responseJson.put("success", false);
            responseJson.put("message", "日期格式不正確");
        } catch (Exception e) {
            responseJson.put("success", false);
            responseJson.put("message", "新增失敗");
        }
        return responseJson.toString();
    }

    @DeleteMapping("/member/creditCard/{userId}/{cardId}")
    public String deleteCard(@PathVariable Integer userId, @PathVariable Integer cardId,
            @RequestHeader("Authorization") String token) throws JSONException {
        JSONObject responseJson = new JSONObject();

        // 驗證user
        if (!jwtUtil.isUser(userId, token)) {
            responseJson.put("success", false);
            responseJson.put("message", "沒有權限");
            return responseJson.toString();
        }
        System.out.println("123");
        try {
            boolean isDeleted = cardService.deleteById(cardId, userId);

            if (isDeleted) {
                responseJson.put("success", true);
                responseJson.put("message", "信用卡刪除成功");
            } else {
                responseJson.put("success", false);
                responseJson.put("message", "找不到該信用卡或刪除失敗");
            }
        } catch (Exception e) {
            responseJson.put("success", false);
            responseJson.put("message", "刪除信用卡時發生錯誤");
        }

        return responseJson.toString();
    }
}
