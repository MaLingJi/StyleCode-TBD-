package com.outfit_share.controller.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.outfit_share.entity.orders.pay.LinePayDTO;
import com.outfit_share.service.orders.PayService;

@RestController
@RequestMapping("/pay")
public class PayController {
	@Autowired
	private PayService payService;
	// 回傳付款網址到前端 若需要更多資訊再依需求改成回傳JSON
	@PostMapping("/linePayRequest")
	public String LpRequest(@RequestBody LinePayDTO lpRequest) throws JsonProcessingException {
		String paymentUrl = payService.requestPayment(lpRequest);
		return paymentUrl;
	}


	@GetMapping("/linePayConfirm")
	public String LinePayConfirm(@RequestParam(value="orderId") String orderId) throws JsonProcessingException {
			String returnCode = payService.confirmPayment(orderId);
			
			
			if (returnCode.equals("0000")) {
				return "sucess paged";
			}
			return returnCode;
	}
}