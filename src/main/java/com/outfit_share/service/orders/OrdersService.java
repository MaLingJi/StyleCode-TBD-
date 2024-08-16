package com.outfit_share.service.orders;

import java.util.List;

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
	
	public List<Orders> findByUserId(Integer Id){
		return ordersRepository.findByUserId(Id);
	}
}
