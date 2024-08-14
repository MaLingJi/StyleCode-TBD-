package com.outfit_share.entity.product;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Integer productId;
	
	@Column(name = "product_name" ,unique = true)
	private String 	productName;
	
	@ManyToOne
	@JoinColumn(name = "subcategory_id")
	private Subcategory subcategoryId;
	
	@Column(name = "price")
	private Integer price;
	
	@Column(name = "stock")
	private Integer stock;
	
	@Column(name = "size")
	private String size;
	
	@Column(name = "color")
	private String color;
	
	@Column(name = "product_description")
	private String productDescription;
	
	@Column(name = "onSale")
	private Integer onSale;
	
	@OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY , mappedBy = "productId")
	private List<Pimages> pimages;
}
