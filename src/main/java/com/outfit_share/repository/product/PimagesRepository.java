package com.outfit_share.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.product.Pimages;

public interface PimagesRepository extends JpaRepository<Pimages, Integer> {
	
	List<Pimages> findByProductIdProductId(Integer productId);
}
