package com.outfit_share.repository.orders;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.outfit_share.entity.orders.Cart;
import com.outfit_share.entity.orders.CartId;

public interface CartRepository extends JpaRepository<Cart, CartId> {
	@Query("select c from Cart c where c.cartId.userId = :uuu and c.cartId.productId = :ppp")
	Cart findByUserIdAndProductId(@Param("uuu") Integer userId,@Param("ppp") Integer productId);
	@Query("select c from Cart c where c.cartId.userId =uuu")
	List<Cart> findByUserId(@Param("uuu") Integer userId);
}
