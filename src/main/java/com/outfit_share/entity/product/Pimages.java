package com.outfit_share.entity.product;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pimages")
public class Pimages {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	private Integer imageId;//照片ID
	
	@ManyToOne
	@JoinColumn(name = "product_id" , nullable = false)
	private Product productId;//商品的ID
	
	@Column(name = "image_name", nullable = false)
	private String imageName;//照片名字
	
	@Column(name = "img_url" , nullable = false)
	private String imgUrl;//照片URL
	
	@Column(name = "image_type")
    private String imageType;  // 有滑鼠移入移出事件測試，切換不同的圖片	
}
