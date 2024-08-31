package com.outfit_share.entity.post;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ImagesDTO {
    private int imageId;
	private String imgUrl;
	private Date deletedAt;

	public ImagesDTO(Images images) {
		this.imageId = images.getImageId();
		this.imgUrl = images.getImgUrl();
		this.deletedAt = images.getDeletedAt();
	}
}
