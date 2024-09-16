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
	
    public String genAioCheckOutALL(OrdersDTO order){
    	
    	System.out.println("start"+order);
    	AllInOne allInOne = new AllInOne("");
    	
		AioCheckOutALL obj = new AioCheckOutALL();
//		obj.setMerchantTradeNo(order.getOrderId());
		obj.setMerchantTradeNo(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10));
		
		obj.setMerchantTradeDate(order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
//		obj.setMerchantTradeDate(order.getOrderDate());
		
		obj.setTotalAmount(order.getTotalAmounts().toString());
//		obj.setTotalAmount(order.toString());
		
		obj.setTradeDesc("商品描述");
		obj.setItemName("商品1 x 1");
		obj.setReturnURL("1|OK");
		obj.setClientBackURL(domainURL + "checkPaying2?orderId=" + obj.getMerchantTradeNo()+"&totalAmounts="+obj.getTotalAmount()); //controller 
		System.out.println("backurl"+obj.getClientBackURL());
		obj.setNeedExtraPaidInfo("N");
		System.out.println("final"+obj);
		String form = allInOne.aioCheckOut(obj, null);
		return form;
		
		
	}
}