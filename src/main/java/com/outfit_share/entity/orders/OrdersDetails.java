package com.outfit_share.entity.orders;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.outfit_share.entity.product.Product;

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
@Table(name = "ordersDetails")
public class OrdersDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "quantity")
	private Integer quantity;


	@ManyToOne
	@JoinColumn(name = "orders_id")
	private Orders orders;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

}
