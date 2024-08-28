package com.outfit_share.entity.orders;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable

public class CartId {
	private Integer userId;
	private Integer productDetailsId;

	@Override
	public int hashCode() {
		return Objects.hash(productDetailsId, userId);
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
		return Objects.equals(productDetailsId, other.productDetailsId) && Objects.equals(userId, other.userId);
	}

}
