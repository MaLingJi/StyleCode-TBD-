package com.outfit_share.service.orders;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
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
import com.outfit_share.entity.orders.Orders;
import com.outfit_share.entity.orders.OrdersDetailsDTO;
import com.outfit_share.entity.orders.TransactionLP;
import com.outfit_share.entity.orders.pay.CheckoutPaymentRequestForm;
import com.outfit_share.entity.orders.pay.ConfirmData;
import com.outfit_share.entity.orders.pay.LinePayDTO;
import com.outfit_share.entity.orders.pay.ProductForm;
import com.outfit_share.entity.orders.pay.ProductPackageForm;
import com.outfit_share.entity.orders.pay.RedirectUrls;
import com.outfit_share.repository.orders.OrdersRepository;
import com.outfit_share.repository.orders.TransLPRepository;
import com.outfit_share.util.Encrypt;

import jakarta.transaction.Transactional;
import lombok.extern.java.Log;

@Service
@Transactional
public class PayService {
	@Autowired
	private ObjectMapper objectMapper; // 注入Jackson的ObjectMapper
	@Autowired
	private TransLPRepository transLPRepo;
	@Autowired
	private OrdersRepository odRepo;

	public String requestPayment(LinePayDTO lpRequest) throws JsonProcessingException {
		// Request API
		System.out.println(lpRequest);

		CheckoutPaymentRequestForm form = new CheckoutPaymentRequestForm();

		form.setAmount(lpRequest.getTotalAmounts());
		form.setCurrency("TWD");
		form.setOrderId(lpRequest.getOrderId());

		ProductPackageForm productPackageForm = new ProductPackageForm();
		productPackageForm.setId(lpRequest.getOrderId());
		productPackageForm.setName("shop_name");
		productPackageForm.setAmount(new BigDecimal(lpRequest.getTotalAmounts()));

//		ProductForm productForm = new ProductForm();
//		productForm.setId("product_id");
//		productForm.setName("product_name");
//		productForm.setImageUrl("");
//		productForm.setQuantity(new BigDecimal("10"));
//		productForm.setPrice(new BigDecimal("10"));

		List<ProductForm> pdList = new ArrayList<>();

		List<OrdersDetailsDTO> odList = lpRequest.getOrderDetails();
		for (OrdersDetailsDTO od : odList) {
			ProductForm productForm = new ProductForm();
			productForm.setId(od.getProductId().toString());
			productForm.setName(od.getProductName());
			productForm.setQuantity(new BigDecimal(od.getQuantity()));
			productForm.setPrice(new BigDecimal(od.getPrice()));
			pdList.add(productForm);
		}

		productPackageForm.setProducts(pdList);

		form.setPackages(Arrays.asList(productPackageForm));

		RedirectUrls redirectUrls = new RedirectUrls();

		redirectUrls.setConfirmUrl("http://localhost:8080/pay/linePayConfirm?orderId=" + form.getOrderId());

		redirectUrls.setCancelUrl("https://claude.ai/chat/641fb415-2ef0-4a44-83e6-f8497e7519e5");

		form.setRedirectUrls(redirectUrls);

		System.out.println(redirectUrls);

		String jsonBody = objectMapper.writeValueAsString(form);

		String ChannelSecret = "6b4e7ed2347de4425b24b016b657f639";
		String requestUri = "/v3/payments/request";
		String nonce = UUID.randomUUID().toString();
		String signature = Encrypt.encrypt(ChannelSecret, ChannelSecret + requestUri + jsonBody + nonce);

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

		if (response != null && response.has("info")) {

			JSONObject info = response.optJSONObject("info");

			Number transactionId = info.getNumber("transactionId");
			String paymentUrl = info.getJSONObject("paymentUrl").getString("web");
			System.out.println(form);
			System.out.println("Payment URL: " + paymentUrl);
			System.out.println("Generated Signature: " + signature);
			System.out.println(transactionId);

			TransactionLP transactionLP = new TransactionLP();
			transactionLP.setAmount(form.getAmount());
			transactionLP.setOrderId(form.getOrderId());
			transactionLP.setTransactionId(transactionId.toString());

			TransactionLP byTransId = transLPRepo.findByOrderId(transactionLP.getOrderId());
			System.out.println(transactionLP.getTransactionId());
			if (byTransId != null) {
				return paymentUrl;
			} else {
				transLPRepo.save(transactionLP);
				return paymentUrl;
			}
		}
		return null;
	}

	public String confirmPayment(String orderId) throws JsonProcessingException {
		TransactionLP order = transLPRepo.findByOrderId(orderId);//
		if (order != null) {
			System.out.println(order);
			ConfirmData confirmData = new ConfirmData();
			confirmData.setAmount(new BigDecimal(order.getAmount()));
			confirmData.setCurrency("TWD");

			String jsonBody = objectMapper.writeValueAsString(confirmData);
			String ChannelSecret = "6b4e7ed2347de4425b24b016b657f639";
			String transactionId = order.getTransactionId();
			System.out.println(transactionId);
			String requestUri = "/v3/payments/" + transactionId + "/confirm";
			String nonce = UUID.randomUUID().toString();
			String signature = Encrypt.encrypt(ChannelSecret, ChannelSecret + requestUri + jsonBody + nonce);

			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("X-LINE-ChannelId", "2005877664");
			headers.set("X-LINE-Authorization-Nonce", nonce);
			headers.set("X-LINE-Authorization", signature);

			HttpEntity<String> httpEntity = new HttpEntity<>(jsonBody, headers);

			ResponseEntity<String> responseEntity = restTemplate.exchange(
					"https://sandbox-api-pay.line.me/v3/payments/" + transactionId + "/confirm", HttpMethod.POST,
					httpEntity, String.class);

			JSONObject response = new JSONObject(responseEntity.getBody());

			System.out.println("confirm response:" + response);

			if (response != null && response.has("returnCode")) {
				String returnCode = response.getString("returnCode");
				if (returnCode.equals("0000")) {
					processScucess(UUID.fromString(orderId));
					return returnCode;
				}
			} else {
				return "wrong code:" + response.getString("returnCode");
			}
		}
		return "cant find order";
	}

	// 改狀態
	public void processScucess(UUID orderId) {
		Optional<Orders> order = odRepo.findById(orderId);
		if (order.isPresent()) {
			Orders orders = order.get();
			if (orders.getStatus() == 0) {
				orders.setStatus(1);
				odRepo.save(orders);
			} else {
				System.out.println("status not 0");
			}
		} else {
			System.out.println("order not exist");
		}
	}
}