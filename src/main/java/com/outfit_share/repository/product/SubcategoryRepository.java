package com.outfit_share.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.product.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {

	   List<Subcategory> findByCategoryCategoryId(Integer categoryId);
	
}
