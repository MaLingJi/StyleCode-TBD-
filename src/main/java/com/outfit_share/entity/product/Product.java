package com.outfit_share.entity.product;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	private Integer productId;//商品ID
	
	@Column(name = "product_name" ,unique = true)
	private String 	productName;//商品名稱
	
	@JsonManagedReference("product_subcategory")
	@ManyToOne
	@JoinColumn(name = "subcategory_id")
	private Subcategory subcategoryId;//子分類
	
	@Column(name = "price")
	private Integer price;//價錢
	
	@Column(name = "stock")
	private Integer stock;//庫存
	
	@Column(name = "size")
	private String size;//尺寸
	
	@Column(name = "color")
	private String color;//顏色
	
	@Column(name = "product_description")
	private String productDescription;//商品說明
	
	@Column(name = "onSale")
	private Integer onSale;//商品狀態(1上架or0下架)
	
	@JsonBackReference("pimages_product")
	@OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY , mappedBy = "productId")
	private List<Pimages> pimages;
}
