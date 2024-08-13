package com.outfit_share.entity.orders;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Embeddable

public class CartId {
	private Integer userId;

	private Integer productId;

	@Override
	public int hashCode() {
		return Objects.hash(productId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartId other = (CartId) obj;
		return Objects.equals(productId, other.productId) && Objects.equals(userId, other.userId);
	}

}
