package com.outfit_share.entity.posts;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "Images")
public class Images {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	private int imageId;
	
	@JoinColumn(name = "post_id")
	private int postId;
	
	@Column(name = "img_url")
	private String imgUrl;
	
	public Images() {
	}

	public Images(int imageId, int postId, String imgUrl) {
		this.imageId = imageId;
		this.postId = postId;
		this.imgUrl = imgUrl;
	}

	public Images(int imageId, String imgUrl) {
		this.imageId = imageId;
		this.imgUrl = imgUrl;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgurl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
