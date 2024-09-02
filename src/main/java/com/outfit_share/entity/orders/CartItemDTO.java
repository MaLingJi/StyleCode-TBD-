package com.outfit_share.entity.orders;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemDTO {
	private Integer userId;
	private Integer productDetailsId;
	private Integer productId;
	private Integer quantity;
	private String productName;
	private Integer productPrice;
	private List<CartItemDTO> items;
	@Override
	public String toString() {
		return "CartItemDTO [userId=" + userId + ", productDetailsId=" + productDetailsId + ", productId=" + productId
				+ ", quantity=" + quantity + ", productName=" + productName + ", productPrice=" + productPrice
				+ ", items=" + items + "]";
	}
	
	
}
