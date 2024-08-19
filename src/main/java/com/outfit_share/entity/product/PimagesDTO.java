package com.outfit_share.entity.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PimagesDTO {

	private Integer imageId;//照片ID
	
	private String imageName;//照片名字
	
	private Integer productId;//商品的ID
	
	private String imgUrl;//照片URL

	public PimagesDTO(Integer imageId, String imageName, Integer productId, String imgUrl) {
		this.imageId = imageId;
		this.imageName = imageName;
		this.productId = productId;
		this.imgUrl = imgUrl;
	}
	
	
}
