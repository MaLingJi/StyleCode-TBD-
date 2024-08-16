package com.outfit_share.service.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.orders.OrdersDetails;
import com.outfit_share.repository.orders.OrdersDetailsRepository;

@Service
public class OrdersDetailsService {
	@Autowired 
	OrdersDetailsRepository odRepo;
	
	public OrdersDetails saveOrderDetails(OrdersDetails od) {
		return odRepo.save(od);
	}
}
