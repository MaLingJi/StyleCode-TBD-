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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.outfit_share.entity.orders.Cart;
import com.outfit_share.entity.orders.Orders;
import com.outfit_share.entity.orders.OrdersDTO;
import com.outfit_share.entity.orders.OrdersDetails;
import com.outfit_share.entity.orders.OrdersDetailsDTO;
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

	//客戶角度看他的訂單
	//改使用DTO作為回傳物件  OK
	@GetMapping("/findByUserId/{id}")
	public List<OrdersDTO> findByUserId(@PathVariable(value = "id") Integer userId,@RequestParam(value = "status",required = false) Integer status) {
		if (status!=null) {
			return ordersService.findByUserIdAndStatus(userId,status);
		}
		return  ordersService.findByUserId(userId);
		
	}
	
	//客戶角度看他得訂單詳情
	//改使用DTO作為回傳物件  OK
	@GetMapping("/findOrderDetails/{ordersId}")
	public List<OrdersDetailsDTO> findOdByOrdersId(@PathVariable(value = "ordersId") UUID ordersId){
		return odService.findOdByOrderId(ordersId);
	}
	// --------------------------------background controller

	//刪除訂單
	//改使用DTO作為回傳物件  OK
	@DeleteMapping("/delete/{orderId}")
	public OrdersDTO deleteByOrderId(@PathVariable(value = "orderId") UUID orderId) {
		OrdersDTO result = ordersService.deleteOrders(orderId);
		result.setStatus(2);
		return result;
	}

	//後台看所有訂單
	//改使用DTO作為回傳物件  OK
	@GetMapping("/findAll")
	public List<OrdersDTO> findAll() {
		return ordersService.findAll();
	}

	// 後台看不同狀態訂單(0=尚未付款 1=已付款 2=已取消) 分析用
	//改使用DTO作為回傳物件  OK
	@GetMapping("/findByStatus/{status}")
	public List<OrdersDTO> findByStatus(@PathVariable  Integer status) {
		return ordersService.findByStatus(status);
	}

	// findOrderDetailsByStatus 抓訂單詳情分析用待補
	
}
