package com.outfit_share.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.product.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {

}
