package com.outfit_share.entity.orders.pay;
import java.util.Arrays;
import java.util.List;

import com.outfit_share.entity.orders.OrdersDetailsDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LinePayDTO {
	private Integer totalAmounts;
	private String orderId;
	private List<OrdersDetailsDTO> orderDetails;
	@Override
	public String toString() {
		return "LinePayDTO [totalAmounts=" + totalAmounts + ", orderId=" + orderId + ", orderDetails=" + orderDetails
				+ "]";
	}
	

}
