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
	@Column(name = "image_id" )
	private Integer imageId;//照片ID
	
	@Column(name = "image_name" ,unique = true)
	private String imageName;//照片名字
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product productId;//商品的ID
	
	
	@Column(name = "img_url")
	private String imgUrl;//照片URL
}
