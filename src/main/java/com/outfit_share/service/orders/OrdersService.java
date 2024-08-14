package com.outfit_share.service.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.orders.Orders;
import com.outfit_share.repository.orders.OrdersRepository;

@Service
public class OrdersService {
	@Autowired
	private OrdersRepository ordersRepository;
	
	public Orders saveOrders(Orders orders) {
		return ordersRepository.save(orders);
	}
}
