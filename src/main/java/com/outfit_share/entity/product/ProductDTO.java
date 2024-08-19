package com.outfit_share.entity.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

	private Integer productId;
	
	private String productName;
	
	private Integer subcategoryId; // 只包含ID，而不是整個Subcategory對象
	
	private String subcategoryName; // 只包含Name，而不是整個Subcategory對象
	
	private Integer price;
	
	private Integer stock;
	
	private String size;
	
	private String color;
	
	private String productDescription;
	
	private boolean onSale;

	public ProductDTO(Product product) {
		this.productId = product.getProductId();
		this.productName = product.getProductName();
		this.price = product.getPrice();
		this.stock = product.getStock();
		this.size = product.getSize();
		this.color = product.getColor();
		this.productDescription = product.getProductDescription();
		this.onSale = product.isOnSale();

		if (product.getSubcategoryId() != null) {
			this.subcategoryId = product.getSubcategoryId().getSubcategoryId();
			this.subcategoryName = product.getSubcategoryId().getSubcategoryName();
		}
	}
}