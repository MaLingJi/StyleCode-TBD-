package com.outfit_share.service.orders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.orders.OrdersDTO;
import com.outfit_share.entity.orders.OrdersDetails;
import com.outfit_share.entity.orders.OrdersDetailsDTO;
import com.outfit_share.repository.orders.OrdersDetailsRepository;

@Service
public class OrdersDetailsService {
	@Autowired 
	private OrdersDetailsRepository odRepo;
	
	public OrdersDetails saveOrderDetails(OrdersDetails od) {
		return odRepo.save(od);
	}
	
	
	//改使用DTO作為回傳物件
	public List<OrdersDetailsDTO> findOdByOrderId(String orderId) {
		List<OrdersDetails> ordersDetails = odRepo.findByOrdersId(orderId);
		List<OrdersDetailsDTO> dtoList = new ArrayList<>();
		
		
		for(OrdersDetails od : ordersDetails) {
			OrdersDetailsDTO odDto = new OrdersDetailsDTO();
			odDto.setId(od.getId());
			odDto.setOrdersId(od.getOrders().getId());
			odDto.setPaymentMethod(od.getOrders().getPayment_method());
			odDto.setPrice(od.getProductDetails().getProductId().getPrice());
			odDto.setProductDetailsId(od.getProductDetails().getProductDetailsId());
			odDto.setProductName(od.getProductDetails().getProductId().getProductName());
			odDto.setQuantity(od.getQuantity());
//			odDto.setSubcatogoryId(od.getProductDetails().getProductId().getSubcategoryId().getSubcategoryId());
//			odDto.setSubcatogoryName(od.getProductDetails().getProductId().getSubcategoryId().getSubcategoryName());
//			odDto.setCatogoryId(od.getProductDetails().getProductId().getSubcategoryId().getCategory().getCategoryId());
//			odDto.setCatogoryName(od.getProductDetails().getProductId().getSubcategoryId().getCategory().getCategoryName());
			dtoList.add(odDto);
		}
		
		return dtoList;

	}
}
