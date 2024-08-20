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
	
//	private Integer subcategoryId; // 只包含子分類ID，而不是整個Subcategory對象
//	
//	private String subcategoryName; // 只包含子分類Name，而不是整個Subcategory對象
//	
//	private String categoriesName; // 大分類的Name
	
	private Integer price;
	
	private Integer stock;
	
	private String size;
	
	private String color;
	
	private String productDescription;
	
	private boolean onSale;
	
	private List<PimagesDTO> pimages; //商品中的照片

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
//			this.subcategoryId = product.getSubcategoryId().getSubcategoryId();
//			this.subcategoryName = product.getSubcategoryId().getSubcategoryName();
//			this.categoriesName = product.getSubcategoryId().getCategory().getCategoryName();
			this.pimages = new ArrayList<>();
			
			for(Pimages pimages : product.getPimages()) {
				if(pimages != null) {
					this.pimages.add(new PimagesDTO(pimages));
				}
			}
		}
	}
}