package com.outfit_share.entity.orders;

import com.outfit_share.entity.product.Product;
import com.outfit_share.entity.users.Users;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

	private Integer vol;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("productId")
	private Product product;
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	private Users users;
}
