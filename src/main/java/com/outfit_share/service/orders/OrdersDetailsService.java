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
			odDto.setPrice(od.getProduct().getPrice());
			odDto.setProductId(od.getProduct().getProductId());
			odDto.setProductName(od.getProduct().getProductName());
			odDto.setQuantity(od.getQuantity());
			dtoList.add(odDto);
		}
		
		return dtoList;
//		for(OrdersDetails od : ordersDetails) {
//			Hibernate.initialize(od.getOrders());
//			Hibernate.initialize(od.getProduct());
//			OrdersDetailsDTO ordersDTO = new OrdersDetailsDTO(od);
//			dtoList.add(ordersDTO);
//		}
//		return dtoList;

//      改使用DTO作為回傳物件 另種寫法
//		return ordersDetails.stream()
//				.map(OrdersDetailsDTO::new)
//		           .collect(Collectors.toList());
//                .map(od -> {
//                    Hibernate.initialize(od.getOrders());
//                    Hibernate.initialize(od.getProduct());
//                    return new OrdersDetailsDTO(od);
//                })
//                .collect(Collectors.toList());
	}
}
