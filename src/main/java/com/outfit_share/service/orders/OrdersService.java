package com.outfit_share.service.orders;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.outfit_share.entity.orders.Cart;
import com.outfit_share.entity.orders.Orders;
import com.outfit_share.entity.orders.OrdersDTO;
import com.outfit_share.entity.orders.OrdersDetails;
import com.outfit_share.entity.product.Product;
import com.outfit_share.entity.product.ProductDetails;
import com.outfit_share.entity.users.UserDetail;
import com.outfit_share.repository.orders.CartRepository;
import com.outfit_share.repository.orders.OrdersDetailsRepository;
import com.outfit_share.repository.orders.OrdersRepository;
import com.outfit_share.repository.product.ProductDetailsRepository;
import com.outfit_share.repository.product.ProductRepository;
import com.outfit_share.repository.users.UserDetailRepository;

@Service
public class OrdersService {
	@Autowired
	private OrdersRepository ordersRepository;
	@Autowired
	private UserDetailRepository udRepo;
	@Autowired
	private CartRepository cartRepo;
	@Autowired
	private OrdersDetailsRepository odRepo;
	@Autowired
	private ProductRepository pdRepo;
	@Autowired
	private ProductDetailsRepository pdDetailRepo;

	public OrdersDTO addOrder(OrdersDTO ordersRequest) {
		List<Cart> cartList = cartRepo.findByUserId(ordersRequest.getUserId());
		
        // 檢查購物車是否為空
        if (cartList.isEmpty()) {
            return null;  // 購物車為空，直接返回 null
        }
		Orders orders = new Orders();
		orders.setTotalAmounts(ordersRequest.getTotalAmounts());
		orders.setId(ordersRequest.getOrderId());
		orders.setStatus(ordersRequest.getStatus());
		Optional<UserDetail> optional = udRepo.findById(ordersRequest.getUserId());
		UserDetail userDetail = optional.get();	
		orders.setUserDetail(userDetail);
		Orders saveOrders = ordersRepository.save(orders);
		
		for (Cart cart : cartList) {
			//save orderDetails
			OrdersDetails ordersDetails = new OrdersDetails();
			ordersDetails.setOrders(orders);
			ordersDetails.setProductDetails(cart.getProductDetails());// need to update
			ordersDetails.setQuantity(cart.getVol());
			odRepo.save(ordersDetails);
			//change stock
			ProductDetails pd = cart.getProductDetails();
			pd.setStock(pd.getStock()-cart.getVol());
			pdDetailRepo.save(pd);
		}
		cartRepo.deleteByUsers(ordersRequest.getUserId()); 
		
		return new OrdersDTO(saveOrders);
		
	}

	public OrdersDTO saveOrders(Orders orders) {
		Orders save = ordersRepository.save(orders);
		return new OrdersDTO(save);
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


	public List<OrdersDTO> findByUserIdAndStatus(Integer userId, Integer status) {
		List<Orders> result = ordersRepository.findByUserIdAndStatus(userId, status);
		List<OrdersDTO> ordersDTOList = new ArrayList<>();
		for (Orders order : result) {
			Hibernate.initialize(order.getUserDetail());
			OrdersDTO ordersDTO = new OrdersDTO(order);
			ordersDTOList.add(ordersDTO);
		}

		return ordersDTOList;
	}

	// 改使用DTO作為回傳物件
	public OrdersDTO deleteOrders(String orderId) {
		Optional<Orders> optionalOrder = ordersRepository.findById(orderId);

		if (optionalOrder.isPresent()) {
			Orders order = optionalOrder.get();
			order.setStatus(2);
			Orders updatedOrder = ordersRepository.save(order);
			return new OrdersDTO(updatedOrder);
		}
		return null;
	}

	public Orders findByOrderId(String orderId) {
		Optional<Orders> optional = ordersRepository.findById(orderId);
		if (optional != null) {
			return optional.get();
		}
		return null;
	}

	public List<OrdersDTO> findAll() {
		List<Orders> list = ordersRepository.findAll();
		List<OrdersDTO> dtoList = new ArrayList<OrdersDTO>();
		for (Orders order : list) {
			Hibernate.initialize(order.getUserDetail());
			OrdersDTO ordersDTO = new OrdersDTO(order);
			dtoList.add(ordersDTO);
		}
		return dtoList;
	}

	public List<OrdersDTO> findByStatus(Integer status) {
		List<Orders> list = ordersRepository.findByStatus(status);
		List<OrdersDTO> dtoList = new ArrayList<OrdersDTO>();
		for (Orders order : list) {
			Hibernate.initialize(order.getUserDetail());
			OrdersDTO ordersDTO = new OrdersDTO(order);
			dtoList.add(ordersDTO);
		}
		return dtoList;
	}
	
	public List<OrdersDTO> findByDate(LocalDateTime startDate, LocalDateTime endDate){
		List<Orders> byDate = ordersRepository.findByDate(startDate, endDate);
		List<OrdersDTO> dtoList = new ArrayList<OrdersDTO>();
		for (Orders order : byDate) {
			OrdersDTO ordersDTO = new OrdersDTO(order);
			dtoList.add(ordersDTO);
		}
		return dtoList;
	}
}
