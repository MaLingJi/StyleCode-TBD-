package com.outfit_share.entity.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PimagesDTO {

	private Integer imageId;//照片ID
	
	private String imageName;//照片名字
	
	private Integer productId;//商品的ID
	
	private String productName;//商品的ID
	
	private String imgUrl;//照片URL

	public PimagesDTO(Pimages pimages) {
		this.imageId = pimages.getImageId();
		this.imageName = pimages.getImageName();
		this.imgUrl = pimages.getImgUrl();
		
		if (pimages.getProductId() != null) {
			this.productId = pimages.getProductId().getProductId();
			this.productName = pimages.getProductId().getProductName();
		}
	}
	
	
}
