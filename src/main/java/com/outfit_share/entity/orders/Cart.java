package com.outfit_share.entity.orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.outfit_share.entity.product.Product;
import com.outfit_share.entity.product.ProductDetails;
import com.outfit_share.entity.users.Users;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "cart")
public class Cart {
	@EmbeddedId
	private CartId cartId;
	
	@Column(name = "vol")
	private Integer vol;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("productDetailsId")
	@JoinColumn(name="productDetails_id")
	
	private ProductDetails productDetails;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	@JoinColumn(name="users_id")
	private Users users;

	
	
}
