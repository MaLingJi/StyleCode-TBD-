package com.outfit_share.entity.orders;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrdersDTO {
	private String orderId;
	private Integer totalAmounts;
	private Integer status;
	private Integer userId;
	private Integer refundStatus;
	private LocalDateTime orderDate;
	private LocalDateTime applyRefundDate;
	private Integer paymentMethod; 
	private String refundReason;
	private List<OrdersDetailsDTO> ordersDetails;

	public OrdersDTO(Orders orders) {
		this.orderId = orders.getId();
		this.totalAmounts = orders.getTotalAmounts();
		this.status = orders.getStatus();
		this.orderDate = orders.getOrderDate();
		this.paymentMethod = orders.getPayment_method();
		this.applyRefundDate = orders.getApplyRefundDate();
		this.refundStatus = orders.getRefundStatus();
		this.refundReason=orders.getRefundReason();
		
		if (orders.getUserDetail() != null) {
			this.userId = orders.getUserDetail().getId();
		}
	}

	@Override
	public String toString() {
		return "OrdersDTO [orderId=" + orderId + ", totalAmounts=" + totalAmounts + ", status=" + status + ", userId="
				+ userId + ", orderDate=" + orderDate + ", paymentMethod=" + paymentMethod + ", ordersDetails="
				+ ordersDetails + "]";
	}
}
