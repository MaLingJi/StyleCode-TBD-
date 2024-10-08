package com.outfit_share.controller.orders;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.outfit_share.entity.orders.OrdersDTO;
import com.outfit_share.entity.orders.OrdersDetailsDTO;
import com.outfit_share.entity.orders.pay.LinePayDTO;
import com.outfit_share.service.orders.PayService;

@RestController
@RequestMapping
public class PayController {
	@Autowired
	private PayService payService;

	// 回傳付款網址到前端 若需要更多資訊再依需求改成回傳JSON
	@PostMapping("/pay/linePayRequest")
	public String LpRequest(@RequestBody LinePayDTO lpRequest) throws JsonProcessingException {
		String paymentUrl = payService.requestPayment(lpRequest);
		return paymentUrl;
	}

	@GetMapping("/pay/linePayConfirm")
	public ResponseEntity<?> linePayConfirm(@RequestParam(value = "orderId") String orderId)
			throws JsonProcessingException {
		String returnCode = payService.confirmPayment(orderId);

		Map<String, Object> response = new HashMap<>();
		if (returnCode.equals("0000")) {
			response.put("status", "success");
			response.put("orderId", orderId);
		} else {
			response.put("status", "error");
			response.put("orderId", orderId);
			response.put("errorCode", returnCode);
		}

		return ResponseEntity.ok(response);
	}

	@PostMapping("/pay/ecPaytoOrder")
	public ResponseEntity<String> ecPaytoOrder(@RequestParam Map<String, String> allParams) {
		String returnCode = payService.ecPaytoOrder(allParams);
		if (returnCode != null) {
			return ResponseEntity.ok("1|OK");
		}
		return ResponseEntity.badRequest().body("0|Failed");
	}

	@PostMapping("/admin/agreeRefund")
	public String agreeRefund(@RequestBody OrdersDTO refundRequest) {
		String refund = payService.refund(refundRequest);
		if (refund == "ok") {
			return "ok";
		}
		return null;
	}

	@PostMapping("/admin/rejectRefund")
	public String rejectRefund(@RequestBody OrdersDTO refundRequest) {
		String refund = payService.rejectRefund(refundRequest);
		if (refund == "ok") {
			return "ok";
		}
		return null;
	}
}