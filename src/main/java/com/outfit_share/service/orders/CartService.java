package com.outfit_share.service.orders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.orders.Cart;
import com.outfit_share.entity.orders.CartId;
import com.outfit_share.repository.orders.CartRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {
	@Autowired
	private CartRepository cartRepository;

	@Transactional
	public Cart addToCart(Integer userId, Integer productId, Integer vol) {
		Cart dbCart = cartRepository.findByUserIdAndProductId(userId, productId);
		if (dbCart != null) {
			dbCart.setVol(dbCart.getVol() + vol);
			return dbCart;
		}

//		Optional<Users> optional = usersRepository.findById(userId);
//		Users users = optional.get();
//		Optional<Photos> optional2 = photosRepository.findById(photoId);
//		Photos photos = optional2.get();

		CartId cartId = new CartId();
		cartId.setProductId(productId);
		cartId.setUserId(userId);

		Cart cart = new Cart();
		cart.setCartId(cartId);
		cart.setVol(vol);
		cart.setProduct(null); // Revise later
		cart.setUsers(null); // Revise later

		return cartRepository.save(cart);
	}

	public List<Cart> findByUserId(Integer userId) {
		List<Cart> result = cartRepository.findByUserId(userId);
		return result;

	}
	
	@Transactional
	public Cart addOneVol(Integer userId,Integer productId) {
		Cart result = cartRepository.findByUserIdAndProductId(userId, productId);
		result.setVol(result.getVol()+1);
		return result;
	}
	
	@Transactional
	public Cart minusOneVol(Integer userId,Integer productId) {
		Cart result = cartRepository.findByUserIdAndProductId(userId, productId);
		if (result.getVol()==1) {
			cartRepository.delete(result);
		}
		else {
			result.setVol(result.getVol()-1);
		}
		return result;
	}
	
	@Transactional
	public void deleteById(Integer userId) {
		cartRepository.deleteByUsers(userId);
	}
	
	
}
