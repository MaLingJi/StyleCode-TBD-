package com.outfit_share.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.outfit_share.entity.product.ProductDetails;

public interface ProductDetailsRepository extends JpaRepository <ProductDetails, Integer>{

//	@Query("FROM ProductDetails where productId.productId = ?1")
//	List<ProductDetails> findByProductId(Integer productId);
	
}
