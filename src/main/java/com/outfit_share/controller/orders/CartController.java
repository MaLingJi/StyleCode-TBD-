package com.outfit_share.controller.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.outfit_share.entity.orders.Cart;
import com.outfit_share.service.orders.CartService;


@RestController("/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	@PostMapping("/add")
	public Cart addToCart(@RequestBody Cart cartRequest) {
		Cart toCart = cartService.addToCart(cartRequest.getCartId().getUserId(), cartRequest.getCartId().getProductId(),cartRequest.getVol());
		return toCart;
	};
	
		// 	showCart
		//	updateCart
}


