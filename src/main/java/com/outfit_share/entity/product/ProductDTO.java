package com.outfit_share.entity.product;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

	private Integer productId;

	private String productName;
	
	private Integer price;//價錢

	private String productDescription;//商品說明
	
	private Integer subcategoryId; // 只包含子分類ID，而不是整個Subcategory對象

	private String subcategoryName; // 只包含子分類Name，而不是整個Subcategory對象

	private List<ProductDetailsDTO> productDetails;

	private List<PimagesDTO> pimages; // 商品中的照片

	public ProductDTO(Product product) {
		this.productId = product.getProductId();
		this.productName = product.getProductName();
		this.price = product.getPrice();
		this.productDescription = product.getProductDescription();
		
		this.subcategoryId = product.getSubcategoryId().getSubcategoryId();
		this.subcategoryName = product.getSubcategoryId().getSubcategoryName();
	
		if(product.getProductDetails() != null) {
			this.productDetails = new ArrayList<>();
			
			for(ProductDetails productDetails : product.getProductDetails()) {
				if(productDetails != null) {
					this.productDetails.add(new ProductDetailsDTO(productDetails));
				}
			}
		}
		
		if (product.getPimages() != null) {

			this.pimages = new ArrayList<>();

			for (Pimages pimages : product.getPimages()) {
				if (pimages != null) {
					this.pimages.add(new PimagesDTO(pimages));
				}
			}
		}
	}
}
