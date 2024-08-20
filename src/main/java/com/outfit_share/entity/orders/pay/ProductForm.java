package com.outfit_share.entity.orders.pay;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductForm {
	private String id;
	private String name;
	private String imageUrl;
	private BigDecimal quantity;
	private BigDecimal price;
}
