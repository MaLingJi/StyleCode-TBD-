package com.outfit_share.controller.orders;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.outfit_share.entity.orders.OrdersDTO;
import com.outfit_share.entity.orders.OrdersDetailsDTO;
import com.outfit_share.entity.orders.RefundDTO;
import com.outfit_share.service.orders.OrdersDetailsService;
import com.outfit_share.service.orders.OrdersService;

@RestController
@RequestMapping
public class OrderController {
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private OrdersDetailsService odService;
	
	 @PostMapping("/order/ecpay")
	    public ResponseEntity<String> createECPayOrder(@RequestBody OrdersDTO ordersDTO) {
	        String htmlForm = ordersService.createECPayOrder(ordersDTO);
	        return ResponseEntity.ok(htmlForm);
	    }

	@PostMapping("/order/add") // 待測試
	// 新增訂單
	public OrdersDTO addOrder(@RequestBody OrdersDTO ordersRequest) {
		OrdersDTO order = ordersService.addOrder(ordersRequest);
		if (order != null) {
			return order;
		}
		return null;
	}
	
	@PostMapping("/order/addec") // 待測試
	// 新增訂單
	public OrdersDTO addOrderec(@RequestBody OrdersDTO ordersRequest) {
		OrdersDTO order = ordersService.addOrder(ordersRequest);
		if (order != null) {
			return order;//這邊要跳傳
		}
		return null;
	}

	// 客戶角度看他的訂單
	// 改使用DTO作為回傳物件 OK
	@GetMapping("/order/find/{userId}")
	public List<OrdersDTO> findByUserId(@PathVariable(value = "userId") Integer userId,
			@RequestParam(value = "status", required = false) Integer status) {
		if (status != null) {
			return ordersService.findByUserIdAndStatus(userId, status);
		}
		return ordersService.findByUserId(userId);

	}

	// 客戶角度看他得訂單詳情
	// 改使用DTO作為回傳物件 OK
	@GetMapping("/order/findOd/{ordersId}")
	public List<OrdersDetailsDTO> findOdByOrdersId(@PathVariable(value = "ordersId") String ordersId) {
		return odService.findOdByOrderId(ordersId);
	}
	// --------------------------------background controller

	// 刪除訂單
	// 改使用DTO作為回傳物件 OK
	@DeleteMapping("/admin/delete/{orderId}")
	public OrdersDTO deleteByOrderId(@PathVariable(value = "orderId") String orderId) {
		OrdersDTO result = ordersService.deleteOrders(orderId);
		result.setStatus(2);
		return result;
	}

	// 後台看所有訂單
	// 改使用DTO作為回傳物件 OK
	@GetMapping("/admin/findAll")
	public List<OrdersDTO> findAll() {
		return ordersService.findAll();
	}

	// 後台看不同狀態訂單(0=尚未付款 1=已付款 2=已取消) 分析用
	// 改使用DTO作為回傳物件 OK
	@GetMapping("/admin/findByStatus/{status}")
	public List<OrdersDTO> findByStatus(@PathVariable Integer status) {
		return ordersService.findByStatus(status);
	}

	@GetMapping("/admin/findByDate")
	public List<OrdersDTO> getOrdersByDateRange(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
		List<OrdersDTO> orders = ordersService.findByDate(startDate, endDate);
		return orders;
	}

	@PostMapping("/order/addRefund")
	public RefundDTO addRefund(@RequestBody RefundDTO refundRequest) {
		RefundDTO refund = ordersService.addRefund(refundRequest);
		if (refund != null) {
			return refund;
		} else {
			return null;
		}
	}

	@GetMapping("/order/findByOrderId/{orderId}")
	public OrdersDTO findByOrderId2(@PathVariable String orderId) {
		OrdersDTO byOrderId = ordersService.findByOrderId(orderId);
		if (byOrderId != null) {
			return byOrderId;
		}
		return null;

	}

	@GetMapping("/admin/findByRefundStatus/{refundStatus}")
	public List<OrdersDTO> findByRefundStatus(@PathVariable Integer refundStatus) {
		List<OrdersDTO> byRefundStatus = ordersService.findByRefundStatus(refundStatus);
		if (byRefundStatus != null) {
			return byRefundStatus;
		}
		return null;

	}

}
