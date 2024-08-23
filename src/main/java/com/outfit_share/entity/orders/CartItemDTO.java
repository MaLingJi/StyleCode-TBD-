package com.outfit_share.entity.orders;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemDTO {
	private Integer userId;
	private Integer productId;
	private Integer quantity;
	private String productName;
	private Integer productPrice;
}
