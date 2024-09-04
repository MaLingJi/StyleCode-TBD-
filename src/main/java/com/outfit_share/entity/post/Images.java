package com.outfit_share.entity.post;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "Images")
public class Images {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	private int imageId;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	@Column(name = "img_url")
	private String imgUrl;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at", nullable = true)
    private Date deletedAt;
	
	public Images(int imageId, Post post, String imgUrl) {
		this.imageId = imageId;
		this.post = post;
		this.imgUrl = imgUrl;
	}

	public Images(int imageId, String imgUrl) {
		this.imageId = imageId;
		this.imgUrl = imgUrl;
	}

	// public Post getPostId() {
	// 	return post;
	// }

	// public void setPostId(Post post) {
	// 	this.post = post;
	// }

	// public void setImgurl(String imgUrl) {
	// 	this.imgUrl = imgUrl;
	// }
	
}
