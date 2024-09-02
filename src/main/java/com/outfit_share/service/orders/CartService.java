package com.outfit_share.service.orders;

import java.io.Console;
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
import com.outfit_share.entity.product.ProductDetails;
import com.outfit_share.entity.users.Users;
import com.outfit_share.repository.orders.CartRepository;
import com.outfit_share.repository.product.ProductDetailsRepository;
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
	@Autowired
	private ProductDetailsRepository prodetailsRepo;

	@Transactional
	public Cart addToCart(Integer userId, Integer productDetailsId, Integer vol) {

		Optional<ProductDetails> productDetails = prodetailsRepo.findById(productDetailsId);
		if (productDetails.isPresent()) {
			if (productDetails.get().getStock() >= vol) {
				Cart dbCart = cartRepository.findByUserIdAndProductId(userId, productDetailsId);
				if (dbCart != null) {
					dbCart.setVol(dbCart.getVol() + vol);
					return dbCart;
				}
				
				Optional<Users> optional = usersRepo.findById(userId);
				Users users = optional.get();

				CartId cartId = new CartId();
				cartId.setProductDetailsId(productDetailsId);
				cartId.setUserId(userId);

				Cart cart = new Cart();
				cart.setCartId(cartId);
				cart.setVol(vol);
				cart.setProductDetails(productDetails.get());
				cart.setUsers(users);

				return cartRepository.save(cart);
			}

			return null;
		}
		return null;
	}

	public Cart updateVol(Integer newVol, Integer productDetailsId, Integer userId) {
		Optional<ProductDetails> optional = prodetailsRepo.findById(productDetailsId);
		if (optional.isPresent()) {
			Integer stock = optional.get().getStock();
			if (stock >= newVol) {
				Cart dbCart = cartRepository.findByUserIdAndProductId(userId, productDetailsId);
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
			dto.setProductDetailsId(cart.getCartId().getProductDetailsId());
			dto.setQuantity(cart.getVol());
			dto.setProductId(cart.getProductDetails().getProductId().getProductId());
			Optional<ProductDetails> optional = prodetailsRepo.findById(cart.getCartId().getProductDetailsId());
			if (optional.isPresent()) {
				Product product = optional.get().getProductId();
				dto.setProductName(product.getProductName());
				dto.setProductPrice(product.getPrice());
			}
			cartIremDTO.add(dto);
		}

		return cartIremDTO;

	}

	@Transactional
	public Cart addOneVol(Integer userId, Integer productDetailsId) {
		Cart result = cartRepository.findByUserIdAndProductId(userId, productDetailsId);
		Optional<ProductDetails> byId = prodetailsRepo.findById(productDetailsId);
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
	public Cart minusOneVol(Integer userId, Integer productDetailsId) {
		Cart result = cartRepository.findByUserIdAndProductId(userId, productDetailsId);
		Optional<ProductDetails> byId = prodetailsRepo.findById(productDetailsId);
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
	public String deleteByUserIdProductId(Integer userId, Integer productDetailsId) {
		Cart dbCart = cartRepository.findByUserIdAndProductId(userId, productDetailsId);
		if (dbCart != null) {
			cartRepository.delete(dbCart);
			return "scucess";
		}
		return null; 
	}

	public String checkStock(CartItemDTO checkRequest) {
		List<CartItemDTO> items = checkRequest.getItems();
		for (CartItemDTO item : items) {
			System.out.println("item :"+item);
			System.out.println("item.getProductDetailsId :"+item.getProductDetailsId());
			System.out.println("item.getQuantity :"+item.getQuantity());
			
			Optional<ProductDetails> byId = prodetailsRepo.findById(item.getProductDetailsId());
			System.out.println("byId.get().getStock() :"+byId.get().getStock());
			if (byId.isPresent()) {
				if (byId.get().getStock() < item.getQuantity()) {
					return null;
				}
			}
		}
		return "ok";
	}
}
