package com.outfit_share.service.orders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.outfit_share.entity.orders.CheckoutPaymentRequestForm;
import com.outfit_share.entity.orders.ProductForm;
import com.outfit_share.entity.orders.ProductPackageForm;
import com.outfit_share.entity.orders.RedirectUrls;
import com.outfit_share.util.Encrypt;

@Service
public class PayService {
	@Autowired
	private ObjectMapper objectMapper; // 注入Jackson的ObjectMapper

	public String requestPayment() throws JsonProcessingException {
		// Request API
		CheckoutPaymentRequestForm form = new CheckoutPaymentRequestForm();

		form.setAmount(new BigDecimal("100"));
		form.setCurrency("TWD");
		form.setOrderId("0ED348D5-A882-46F6-A3E9-69EB3456C687");

		ProductPackageForm productPackageForm = new ProductPackageForm();
		productPackageForm.setId("package_id");
		productPackageForm.setName("shop_name");
		productPackageForm.setAmount(new BigDecimal("100"));

		ProductForm productForm = new ProductForm();
		productForm.setId("product_id");
		productForm.setName("product_name");
		productForm.setImageUrl("");
		productForm.setQuantity(new BigDecimal("10"));
		productForm.setPrice(new BigDecimal("10"));
		productPackageForm.setProducts(Arrays.asList(productForm));

		form.setPackages(Arrays.asList(productPackageForm));
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setConfirmUrl("https://music.youtube.com/");
		redirectUrls.setCancelUrl("https://claude.ai/chat/641fb415-2ef0-4a44-83e6-f8497e7519e5");
		form.setRedirectUrls(redirectUrls);

		String jsonBody = objectMapper.writeValueAsString(form);

		String ChannelSecret = "6b4e7ed2347de4425b24b016b657f639";
		String requestUri = "/v3/payments/request";
		String nonce = UUID.randomUUID().toString();
		String signature = Encrypt.encrypt(ChannelSecret, ChannelSecret + requestUri + jsonBody + nonce);

		// Confirm API
//				ConfirmData confrimData = new ConfirmData();
//				confrimData.setAmount(new BigDecimal("100"));
//				confrimData.setCurrency("TWD");

		// ============================================================================================================
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-LINE-ChannelId", "2005877664");
		headers.set("X-LINE-Authorization-Nonce", nonce);
		headers.set("X-LINE-Authorization", signature);

		HttpEntity<String> httpEntity = new HttpEntity<>(jsonBody, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"https://sandbox-api-pay.line.me/v3/payments/request", HttpMethod.POST, httpEntity, String.class);

		JSONObject response = new JSONObject(responseEntity.getBody());
		System.out.println(response);
		if (response != null) {
			String returnCode = response.getString("returnCode");
			String returnMessage = response.getString("returnMessage");
			String paymentUrl = "No Payment URL";
			JSONObject info = response.optJSONObject("info");
			if (info != null && info.has("paymentUrl")) {
				paymentUrl = info.getJSONObject("paymentUrl").optString("web", "No Payment URL");
			}
			System.out.println(form);
			System.out.println("Return Code: " + returnCode);
			System.out.println("Return Message: " + returnMessage);
			System.out.println("Payment URL: " + paymentUrl);
			System.out.println("Generated Signature: " + signature);
		}
		return response.getJSONObject("info").getJSONObject("paymentUrl").getString("web"); 
	}
}
