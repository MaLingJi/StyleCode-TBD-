package com.outfit_share.entity.product;

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
	private Integer imageId;
	
	@Column(name = "image_name")
	private String imageName;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	
	@Column(name = "img_url")
	private String imgUrl;
}
