package com.outfit_share.service.orders;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.orders.OrdersDTO;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;



@Service
public class ECPayService {
	
	@Value("${domain.url}")
	private String domainURL;
	@Value("${domain.url2}")
	private String domainURL2;
	
    public String genAioCheckOutALL(OrdersDTO order){
    	
    
    	AllInOne allInOne = new AllInOne("");
    	
		AioCheckOutALL obj = new AioCheckOutALL();
		
		obj.setMerchantTradeNo(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10));
		
		obj.setMerchantTradeDate(order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
		
		obj.setTotalAmount(order.getTotalAmounts().toString());
		
		obj.setTradeDesc("商品描述");
		obj.setItemName("商品1 x 1");
		obj.setReturnURL("1|OK");
		
		//綠界會回傳一個POST 指定後端產生訂單API
		obj.setOrderResultURL(domainURL2 + "pay/ecPaytoOrder");
		//使用者按了回到首頁會回到他的清單
		obj.setClientBackURL(domainURL+"/order");
		obj.setNeedExtraPaidInfo("N");
		obj.setCustomField1(order.getUserId().toString());
		
		String form = allInOne.aioCheckOut(obj, null);
		return form;
		
		
	}
}