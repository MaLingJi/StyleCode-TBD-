package com.outfit_share.entity.orders;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersDTO {
	private UUID id;
	private Integer totalAmounts;
	private Integer status;
	private Integer userId;
	
	public OrdersDTO(Orders orders) {
		this.id=orders.getId();
		this.totalAmounts=orders.getTotalAmounts();
		this.status=orders.getStatus();
		if (orders.getUserDetail()!=null) {
			this.userId=orders.getUserDetail().getId();
		}
	}
	
}
