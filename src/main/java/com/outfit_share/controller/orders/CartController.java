package com.outfit_share.controller.orders;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.outfit_share.entity.orders.Cart;
import com.outfit_share.entity.orders.CartItemDTO;
import com.outfit_share.service.orders.CartService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {
	@Autowired
	private CartService cartService;

	@PostMapping("/add")
	public Cart addToCart(@RequestBody Cart cartRequest) {
		Cart toCart = cartService.addToCart(cartRequest.getCartId().getUserId(), cartRequest.getCartId().getProductId(),
				cartRequest.getVol());
		if (toCart != null) {
			return toCart;
		}
		return null;
	};

	@GetMapping("/find/{id}")
	public List<CartItemDTO> findCartById(@PathVariable(value = "id") Integer id) {
		List<CartItemDTO> result = cartService.findByUserId(id);
		return result;
	}

	@PutMapping("/update")
	public Cart updateCartVol(@RequestBody Cart cartRequest) {
		Cart updateVolCart = cartService.updateVol(cartRequest.getVol(), cartRequest.getCartId().getProductId(),
				cartRequest.getCartId().getUserId());
		if (updateVolCart != null) {
			return updateVolCart;
		}

		return null;
	}

	@DeleteMapping("/delete")
	public String deleteCart(@RequestBody CartItemDTO cartRequest) {
		String message = cartService.deleteByUserIdProductId(cartRequest.getUserId(), cartRequest.getProductId());
		if(message=="scucess") {
			return "scucess";
		}
		return null;
	}

	@PostMapping("/addOneVol")
	public Cart addVol(@RequestBody CartItemDTO cartRequest) {
		Cart result = cartService.addOneVol(cartRequest.getUserId(), cartRequest.getProductId());
		if (result != null) {
			return result;
		}
		return null;
	}

	@PostMapping("/minusOneVol")
	public Cart minusOneVol(@RequestBody Cart cartRequest) {
		if (cartRequest.getCartId() != null) {
			Cart result = cartService.minusOneVol(cartRequest.getCartId().getUserId(),
					cartRequest.getCartId().getProductId());
			return result;
		}
		return null;
	}

}
