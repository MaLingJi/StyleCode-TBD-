package com.outfit_share.service.orders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.outfit_share.entity.orders.CartItemDTO;
import com.outfit_share.entity.orders.Orders;
import com.outfit_share.entity.orders.OrdersDTO;
import com.outfit_share.entity.orders.OrdersDetails;
import com.outfit_share.entity.orders.TransactionLP;
import com.outfit_share.entity.orders.pay.CheckoutPaymentRequestForm;
import com.outfit_share.entity.orders.pay.ConfirmData;
import com.outfit_share.entity.orders.pay.LinePayDTO;
import com.outfit_share.entity.orders.pay.ProductForm;
import com.outfit_share.entity.orders.pay.ProductPackageForm;
import com.outfit_share.entity.orders.pay.RedirectUrls;
import com.outfit_share.entity.product.ProductDetails;
import com.outfit_share.entity.users.Notifications;
import com.outfit_share.entity.users.UserDetail;
import com.outfit_share.repository.orders.OrdersDetailsRepository;
import com.outfit_share.repository.orders.OrdersRepository;
import com.outfit_share.repository.orders.TransLPRepository;
import com.outfit_share.repository.product.ProductDetailsRepository;
import com.outfit_share.repository.users.NotificationsRepository;
import com.outfit_share.util.Encrypt;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PayService {
	@Autowired
	private ObjectMapper objectMapper; // 注入Jackson的ObjectMapper
	@Autowired
	private TransLPRepository transLPRepo;
	@Autowired
	private OrdersRepository odRepo;
	@Autowired
	private OrdersService odService;
	@Autowired
	private OrdersDetailsRepository ordersDetailsRepo;
	@Autowired
	private ProductDetailsRepository pdRepo;

	@Value("${domain.url}")
	private String domainURL;

	@Value("${channelId}")
	private String channelId;

	@Value("${channelSecret}")
	private String channelSecret;

	@Autowired
	private NotificationsRepository notiRepo;

	public String requestPayment(LinePayDTO lpRequest) throws JsonProcessingException {

		System.out.println(lpRequest);

		CheckoutPaymentRequestForm form = new CheckoutPaymentRequestForm();
		String tempOrderId = UUID.randomUUID().toString();
		form.setAmount(lpRequest.getTotalAmounts());
		form.setCurrency("TWD");
		form.setOrderId(tempOrderId);

		ProductPackageForm productPackageForm = new ProductPackageForm();
		productPackageForm.setId(tempOrderId);
		productPackageForm.setName("MDFK");
		productPackageForm.setAmount(new BigDecimal(lpRequest.getTotalAmounts()));

		List<ProductForm> pdList = new ArrayList<>();

		List<CartItemDTO> itemsList = lpRequest.getItems();
		for (CartItemDTO item : itemsList) {
			ProductForm productForm = new ProductForm();
			productForm.setId(item.getProductDetailsId().toString());
			productForm.setName(item.getProductName());
			productForm.setQuantity(new BigDecimal(item.getQuantity()));
			productForm.setPrice(new BigDecimal(item.getProductPrice()));
			pdList.add(productForm);
		}

		productPackageForm.setProducts(pdList);

		form.setPackages(Arrays.asList(productPackageForm));

		RedirectUrls redirectUrls = new RedirectUrls();

		redirectUrls.setConfirmUrl(domainURL + "checkPaying?orderId=" + form.getOrderId());

		redirectUrls.setCancelUrl("https://claude.ai/chat/641fb415-2ef0-4a44-83e6-f8497e7519e5");

		form.setRedirectUrls(redirectUrls);

		System.out.println(redirectUrls);

		String jsonBody = objectMapper.writeValueAsString(form);

		String ChannelSecret = channelSecret;
		String requestUri = "/v3/payments/request";
		String nonce2 = UUID.randomUUID().toString();
		String signature = Encrypt.encrypt(ChannelSecret, ChannelSecret + requestUri + jsonBody + nonce2);

		// ============================================================================================================
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-LINE-ChannelId", channelId);
		headers.set("X-LINE-Authorization-Nonce", nonce2);
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
			transactionLP.setUserId(lpRequest.getUserId());

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
			String ChannelSecret = channelSecret;
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

					OrdersDTO ordersDTO = new OrdersDTO();
					ordersDTO.setOrderId(orderId);
					ordersDTO.setTotalAmounts(order.getAmount());
					ordersDTO.setStatus(1);
					ordersDTO.setUserId(order.getUserId());

					OrdersDTO order2 = odService.addOrder(ordersDTO);
					System.out.println(order2);
					// processScucess(UUID.fromString(orderId));
					return returnCode;
				}
			} else {
				return "wrong code:" + response.getString("returnCode");
			}
		}
		return "cant find order";
	}
	
	public String ecPaytoOrder(OrdersDTO orderId) {
		OrdersDTO ordersDTO = new OrdersDTO();
		ordersDTO.setOrderId(orderId.getOrderId());
		ordersDTO.setTotalAmounts(orderId.getTotalAmounts());
		ordersDTO.setStatus(1);
		ordersDTO.setUserId(orderId.getUserId());
		OrdersDTO order2 = odService.addOrder(ordersDTO);
		if(order2!=null) {
			return "ok";
		}
		return null;
	}
	
	
	public String refund(OrdersDTO refundRequest) {
		String orderId = refundRequest.getOrderId();
		String transactionId = transLPRepo.findByOrderId(orderId).getTransactionId();
		String requestUri = "/v3/payments/" + transactionId + "/refund";
		String nonce = UUID.randomUUID().toString();

		channelSecret = channelSecret.trim();
		String requestBody = "{}";
		String signature = Encrypt.encrypt(channelSecret, channelSecret + requestUri + requestBody + nonce);

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-LINE-ChannelId", channelId);
		headers.set("X-LINE-Authorization-Nonce", nonce);
		headers.set("X-LINE-Authorization", signature);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<>("{}", headers);
		String fullUrl = "https://sandbox-api-pay.line.me" + requestUri;
		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange(fullUrl, HttpMethod.POST, httpEntity,
					String.class);

			String responseBody = responseEntity.getBody();
			System.out.println("Full response: " + responseBody);

			JSONObject response = new JSONObject(responseBody);

			if (response != null && response.has("returnCode")) {
				String returnCode = response.getString("returnCode");
				if (returnCode.equals("0000")) {
					List<OrdersDetails> orderDetailsList = ordersDetailsRepo.findByOrdersId(orderId);
					for (OrdersDetails od : orderDetailsList) {
						ProductDetails productDetails = od.getProductDetails();
						productDetails.setStock(productDetails.getStock() + od.getQuantity());
						pdRepo.save(productDetails);
					}
					Optional<Orders> byId = odRepo.findById(orderId);
					if (byId.isPresent()) {
						Orders orders = byId.get();
						orders.setStatus(3);
						orders.setRefundStatus(2);
						odRepo.save(orders);

						// 創建新通知
						UserDetail orderOwner = orders.getUserDetail();
						Notifications notification = new Notifications();
						notification.setMessage("您的訂單" + orders.getId() + "退款已通過審核");
						notification.setCreatedTime(new Date());
						notification.setStatus(0);
						notification.setType("shop");
						notification.setUserDetail(orderOwner);
						notiRepo.save(notification);
					}
					return "ok";
				}
				return "Error code: " + returnCode + ", Message: " + response.optString("returnMessage");
			}
		} catch (Exception e) {
			System.out.println("Exception occurred: " + e.getMessage());
			e.printStackTrace();
			return "Error: " + e.getMessage();
		}

		return "Unexpected error";
	}

	public String rejectRefund(OrdersDTO refundRequest) {
		String orderId = refundRequest.getOrderId();
		Optional<Orders> byId = odRepo.findById(orderId);
		if (byId.isPresent()) {
			Orders orders = byId.get();
			orders.setStatus(1);
			orders.setRefundStatus(2);
			odRepo.save(orders);

			// 創建新通知
			UserDetail orderOwner = orders.getUserDetail();
			Notifications notification = new Notifications();
			notification.setMessage("您的訂單" + orders.getId() + "退款未通過審核");
			notification.setCreatedTime(new Date());
			notification.setStatus(0);
			notification.setType("shop");
			notification.setUserDetail(orderOwner);
			notiRepo.save(notification);
			return "ok";
		}
		return null;
	}
}