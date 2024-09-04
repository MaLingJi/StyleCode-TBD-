package com.outfit_share.entity.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PimagesDTO {

	private Integer imageId;// 照片ID

	private String imageName;// 照片名字

	private Integer productId;// 商品的ID

	private String productName;// 商品的ID

	private String imgUrl;// 照片URL

//	private String imageType; //有滑鼠移入移出事件測試，切換不同的圖片	

	public PimagesDTO(Pimages pimages) {
		this.imageId = pimages.getImageId();
		this.imageName = pimages.getImageName();
		this.imgUrl = pimages.getImgUrl();
//		this.imageType = pimages.getImageType(); //有滑鼠移入移出事件測試，切換不同的圖片	

		if (pimages.getProductId() != null) {
			this.productId = pimages.getProductId().getProductId();
			this.productName = pimages.getProductId().getProductName();
		}
	}

}
