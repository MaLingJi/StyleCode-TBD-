package com.outfit_share.entity.orders;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ProductPackageForm {
	private String id;
	private String name;
	private BigDecimal amount;
	private List<ProductForm> products;
}
