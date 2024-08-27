package com.outfit_share.entity.orders.pay;

import java.util.List;

import com.outfit_share.entity.orders.CartItemDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LinePayDTO {
	private Integer totalAmounts;
	private List<CartItemDTO> items;
	private Integer userId;
	@Override
	public String toString() {
		return "LinePayDTO [totalAmounts=" + totalAmounts + ", items=" + items + ", userId=" + userId + "]";
	}
	
}
