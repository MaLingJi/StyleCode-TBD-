package com.outfit_share.controller.orders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private CartService cartService;
	@Autowired
	private OrdersDetailsService odService;
	@PostMapping("/addOrder")
	public Orders addOrder(@RequestBody Orders ordersRequest) {
		Orders orders = new Orders();
		orders.setTotalAmounts(ordersRequest.getTotalAmounts());
		orders.setUserDetail(null);//need to update
		Orders saveOrder = ordersService.saveOrders(orders);
		
		List<Cart> byUserId = cartService.findByUserId(ordersRequest.getUserDetail().getId());
		for(Cart cart:byUserId) {
			OrdersDetails ordersDetails = new OrdersDetails();
			ordersDetails.setOrders(saveOrder);
			ordersDetails.setProduct(cart.getProduct());//need to update
			ordersDetails.setQuantity(cart.getVol());
			odService.saveOrderDetails(ordersDetails);
		}
		cartService.deleteById(ordersRequest.getUserDetail().getId());
		return orders;
	}
	
	@GetMapping("/findByUserId/{id}")
	public List<Orders> findByUserId(@PathVariable(value = "id") Integer userId) {
		List<Orders> byUserId = ordersService.findByUserId(userId);
		return byUserId;
	}
	
	
//	deleteOrderById
}
