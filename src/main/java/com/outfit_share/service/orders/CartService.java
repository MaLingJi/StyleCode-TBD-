package com.outfit_share.service.orders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.orders.Cart;
import com.outfit_share.entity.orders.CartId;
import com.outfit_share.entity.orders.CartItemDTO;
import com.outfit_share.entity.product.Product;
import com.outfit_share.entity.users.Users;
import com.outfit_share.repository.orders.CartRepository;
import com.outfit_share.repository.product.ProductRepository;
import com.outfit_share.repository.users.UsersRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private UsersRepository usersRepo;
	@Autowired
	private ProductRepository proRepo;

	@Transactional
	public Cart addToCart(Integer userId, Integer productId, Integer vol) {
		Optional<Product> option2 = proRepo.findById(productId);
		Product product = option2.get();
		if (product.getStock() >= vol) {
			Cart dbCart = cartRepository.findByUserIdAndProductId(userId, productId);
			if (dbCart != null) {
				dbCart.setVol(dbCart.getVol() + vol);
				return dbCart;
			}

			Optional<Users> optional = usersRepo.findById(userId);
			Users users = optional.get();

			CartId cartId = new CartId();
			cartId.setProductId(productId);
			cartId.setUserId(userId);

			Cart cart = new Cart();
			cart.setCartId(cartId);
			cart.setVol(vol);
			cart.setProduct(product);
			cart.setUsers(users);

			return cartRepository.save(cart);
		}

		return null;
	}

	public Cart updateVol(Integer newVol, Integer productId, Integer userId) {
		Optional<Product> optional = proRepo.findById(productId);
		if (optional.isPresent()) {
			Product product = optional.get();
			if (product.getStock() >= newVol) {
				Cart dbCart = cartRepository.findByUserIdAndProductId(userId, productId);
				dbCart.setVol(newVol);
				cartRepository.save(dbCart);
				return dbCart;
			}
		}
		return null;
	}

	public List<CartItemDTO> findByUserId(Integer userId) {
		List<Cart> result = cartRepository.findByUserId(userId);
		List<CartItemDTO> cartIremDTO = new ArrayList<>();

		for (Cart cart : result) {
			CartItemDTO dto = new CartItemDTO();
			dto.setUserId(cart.getCartId().getUserId());
			dto.setProductId(cart.getCartId().getProductId());
			dto.setQuantity(cart.getVol());
			Optional<Product> optional = proRepo.findById(cart.getCartId().getProductId());
			if (optional.isPresent()) {
				Product product = optional.get();
				dto.setProductName(product.getProductName());
				dto.setProductPrice(product.getPrice());
			}
			cartIremDTO.add(dto);
		}

		return cartIremDTO;

	}

	@Transactional
	public Cart addOneVol(Integer userId, Integer productId) {
		Cart result = cartRepository.findByUserIdAndProductId(userId, productId);
		Optional<Product> byId = proRepo.findById(productId);
		if (byId.isPresent()) {
			Integer stock = byId.get().getStock();
			if (result.getVol() + 1 <= stock) {
				result.setVol(result.getVol() + 1);
				cartRepository.save(result);
				return result;
			}
			return null;
		}
		return null;
	}

	@Transactional
	public Cart minusOneVol(Integer userId, Integer productId) {
		Cart result = cartRepository.findByUserIdAndProductId(userId, productId);
		Optional<Product> byId = proRepo.findById(productId);
		if (byId.isPresent()) {
			Integer stock = byId.get().getStock();
			if (result.getVol() == 1) {
				cartRepository.delete(result);
			}

			if (result.getVol() - 1 <= stock) {
				result.setVol(result.getVol() - 1);
				cartRepository.save(result);
				return result;
			}
			
			if (result.getVol() - 1 > stock) {
				result.setVol(result.getVol() - 1);
				cartRepository.save(result);
				return null;
			}
			return null;

		}
		return null;
	}

	// 訂單產生刪除用
	@Transactional
	public void deleteById(Integer userId) {
		cartRepository.deleteByUsers(userId);
	}

	// 購物車刪除商品用
	@Transactional
	public String deleteByUserIdProductId(Integer userId, Integer productId) {
		Cart dbCart = cartRepository.findByUserIdAndProductId(userId, productId);
		if (dbCart != null) {
			cartRepository.delete(dbCart);
			return "scucess";
		}
		return null;
	}
}
