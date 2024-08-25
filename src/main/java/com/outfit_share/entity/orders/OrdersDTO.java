package com.outfit_share.entity.orders;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrdersDTO {
	private UUID id;
	private Integer totalAmounts;
	private Integer status;
	private Integer userId;
	private Date orderDate;
	
	public OrdersDTO(Orders orders) {
		this.id=orders.getId();
		this.totalAmounts=orders.getTotalAmounts();
		this.status=orders.getStatus();
		this.orderDate=orders.getOrderDate();
		if (orders.getUserDetail()!=null) {
			this.userId=orders.getUserDetail().getId();
		}
	}
	
}
