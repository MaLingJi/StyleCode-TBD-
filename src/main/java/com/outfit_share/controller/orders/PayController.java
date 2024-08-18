package com.outfit_share.controller.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.outfit_share.service.orders.PayService;

//linepay 綠界 (要記得付款完成要處理庫存跟改變OrderStatus)

@RestController
@RequestMapping("/pay")
public class PayController {
	@Autowired
	private PayService payService;

	@GetMapping("/linePayRequest")
	public String LpRequest() throws JsonProcessingException {
		String paymentUrl = payService.requestPayment();
		return paymentUrl;
	}
//	@GetMapping("/linePayConfirm")
//	public String LpRequestConfirm(){
//
//	}
}