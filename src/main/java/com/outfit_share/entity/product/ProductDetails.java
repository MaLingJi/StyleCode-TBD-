package com.outfit_share.entity.product;

import java.util.List;

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
@Table(name = "productDetails")
public class ProductDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="product_details_id")
	private Integer productDetailsId;//詳細商品ID
	
	@ManyToOne
	@JoinColumn(name = "product_id" ,nullable = false)
	private Product productId;//商品ID

	@Column(name = "stock" , nullable = false )
	private Integer stock;//庫存
	
	@Column(name = "size" , nullable = false)
	private String size;//尺寸
	
	@Column(name = "color" , nullable = false)
	private String color;//顏色
	
	@Column(name = "onSale" , nullable = false)
	private Boolean onSale;//商品狀態(1上架or0下架)
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "productDetails")
	private List<OrdersDetails> ordersDetails;
	
}
