package com.outfit_share.entity.product;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.outfit_share.entity.orders.OrdersDetails;

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
	private Integer productId;// 商品ID

	@Column(name = "product_name", unique = true, nullable = false)
	private String productName;// 商品名稱

	@Column(name = "price" , nullable = false)
	private Integer price;//價錢
	
	@Column(name = "product_description" , nullable = false)
	private String productDescription;//商品說明
	
	@ManyToOne
	@JoinColumn(name = "subcategory_id", nullable = false)
	private Subcategory subcategoryId;// 子分類

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "productId" )
	private List<ProductDetails> productDetails;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "productId")
	private List<Pimages> pimages;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
	private List<OrdersDetails> ordersDetails;
	
	
	public void addProductDetail(ProductDetails detail) {
        productDetails.add(detail);
        detail.setProductId(this);
    }
}
