package com.outfit_share.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.product.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
