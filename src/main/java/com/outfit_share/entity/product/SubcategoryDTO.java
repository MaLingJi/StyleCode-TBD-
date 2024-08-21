package com.outfit_share.entity.product;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubcategoryDTO {

	private Integer subcategoryId;// 子分類ID

	private String subcategoryName;// 子分類名稱

//	private Integer categoryId;// 分類ID

	private String categoryName;// 分類名稱

	private List<ProductDTO> product;// 子分類中的商品

	public SubcategoryDTO(Subcategory subcategory) {
		this.subcategoryId = subcategory.getSubcategoryId();
		this.subcategoryName = subcategory.getSubcategoryName();

//			this.categoryId = subcategory.getCategory().getCategoryId();
		this.categoryName = subcategory.getCategory().getCategoryName();
		
		if (subcategory.getProduct() != null) {

			this.product = new ArrayList<>();
			for (Product product : subcategory.getProduct()) {
				if (product != null) {
					this.product.add(new ProductDTO(product));
				}
			}
		}
	}

}
