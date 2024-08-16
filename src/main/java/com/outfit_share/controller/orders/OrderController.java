package com.outfit_share.controller.orders;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outfit_share.entity.orders.Cart;
import com.outfit_share.entity.orders.Orders;
import com.outfit_share.entity.orders.OrdersDetails;
import com.outfit_share.service.orders.CartService;
import com.outfit_share.service.orders.OrdersDetailsService;
import com.outfit_share.service.orders.OrdersService;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private CartService cartService;
	@Autowired
	private OrdersDetailsService odService;


	@PostMapping("/addOrder") //待測試
	// 新增訂單
	public Orders addOrder(@RequestBody Orders ordersRequest) {
		Orders orders = new Orders();
		orders.setTotalAmounts(ordersRequest.getTotalAmounts());
		orders.setUserDetail(null);// need to update
		Orders saveOrder = ordersService.saveOrders(orders);

		List<Cart> byUserId = cartService.findByUserId(ordersRequest.getUserDetail().getId());
		for (Cart cart : byUserId) {
			OrdersDetails ordersDetails = new OrdersDetails();
			ordersDetails.setOrders(saveOrder);
			ordersDetails.setProduct(cart.getProduct());// need to update
			ordersDetails.setQuantity(cart.getVol());
			odService.saveOrderDetails(ordersDetails);
		}
		cartService.deleteById(ordersRequest.getUserDetail().getId());
		return orders;
	}

	// 客戶角度看他的訂單
	@GetMapping("/findByUserId/{id}")
	public List<Orders> findByUserId(@PathVariable(value = "id") Integer userId) {
		List<Orders> byUserId = ordersService.findByUserId(userId);
		return byUserId;
	}
	
	// 客戶角度看他得訂單詳情
	@GetMapping("/findOrderDetails/{orderId}")
	public List<OrdersDetails> findOdByOrdersId(UUID ordersId){
		return odService.findOdByOrderId(ordersId);
	}
	// --------------------------------background controller

	// 刪除訂單
	@DeleteMapping("/delete/{orderId}")
	public Orders deleteByOrderId(@PathVariable(value = "orderId") UUID orderId) {
		Orders result = ordersService.findByOrderId(orderId);
		result.setStatus(2);
		return result;
	}

	// 後台看所有訂單
	@GetMapping("/findAll")
	public List<Orders> findAll() {
		return ordersService.findAll();
	}

	// 後台看不同狀態訂單(0=尚未付款 1=已付款 2=已取消) 分析用
	@GetMapping("/findByStatus/{status}")
	public List<Orders> findByStatus(Integer status) {
		return ordersService.findByStatus(status);
	}

	// findOrderDetailsByStatus 抓訂單詳情分析用待補

}
