package com.outfit_share.service.orders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.orders.Orders;
import com.outfit_share.entity.orders.OrdersDTO;
import com.outfit_share.repository.orders.OrdersRepository;

@Service
public class OrdersService {
	@Autowired
	private OrdersRepository ordersRepository;

	public Orders saveOrders(Orders orders) {
		return ordersRepository.save(orders);
	}

	// 改使用DTO作為回傳物件
	public List<OrdersDTO> findByUserId(Integer id) {
		List<Orders> result = ordersRepository.findByUserId(id);
		List<OrdersDTO> ordersDTOList = new ArrayList<>();

		for (Orders order : result) {
			Hibernate.initialize(order.getUserDetail());
			OrdersDTO ordersDTO = new OrdersDTO(order);
			ordersDTOList.add(ordersDTO);
		}

		return ordersDTOList;
	}

	// 改使用DTO作為回傳物件 另種寫法
//		public List<OrdersDTO> findByUserId(Integer Id){
//			List<Orders> result = ordersRepository.findByUserId(Id);
//			return result.stream()
//					.map(od->{
//						Hibernate.initialize(od.getUserDetail());
//						return new OrdersDTO(od);
//					})
//					.collect(Collectors.toList());
//			
//		}
	
	public List<OrdersDTO> findByUserIdAndStatus(Integer userId,Integer status){
		List<Orders> result = ordersRepository.findByUserIdAndStatus(userId,status);
		List<OrdersDTO> ordersDTOList = new ArrayList<>();
		for (Orders order : result) {
			Hibernate.initialize(order.getUserDetail());
			OrdersDTO ordersDTO = new OrdersDTO(order);
			ordersDTOList.add(ordersDTO);
		}

		return ordersDTOList;
	}
	
	

	// 改使用DTO作為回傳物件
	public OrdersDTO deleteOrders(UUID orderId) {
		Optional<Orders> optionalOrder = ordersRepository.findById(orderId);

		if (optionalOrder.isPresent()) {
			Orders order = optionalOrder.get();
			order.setStatus(2);
			Orders updatedOrder = ordersRepository.save(order);
			return new OrdersDTO(updatedOrder);
		}
		return null;
	}

	public Orders findByOrderId(UUID orderId) {
		Optional<Orders> optional = ordersRepository.findById(orderId);
		if (optional != null) {
			return optional.get();
		}
		return null;
	}

	public List<OrdersDTO> findAll() {
		List<Orders> list = ordersRepository.findAll();
		List<OrdersDTO> dtoList = new ArrayList<OrdersDTO>();
		for(Orders order : list) {
			Hibernate.initialize(order.getUserDetail());
			OrdersDTO ordersDTO = new OrdersDTO(order);
			dtoList.add(ordersDTO);
		}
		return dtoList;
	}

	public List<OrdersDTO> findByStatus(Integer status) {
		List<Orders> list = ordersRepository.findByStatus(status);
		List<OrdersDTO> dtoList = new ArrayList<OrdersDTO>();
		for(Orders order : list) {
			Hibernate.initialize(order.getUserDetail());
			OrdersDTO ordersDTO = new OrdersDTO(order);
			dtoList.add(ordersDTO);
		}
		return dtoList;
	}
}
