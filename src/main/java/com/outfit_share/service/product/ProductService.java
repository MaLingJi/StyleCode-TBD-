package com.outfit_share.service.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.product.Product;
import com.outfit_share.repository.product.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
//	新增商品
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}
	
//	修改商品
	public Product updateProduct(Integer id, Product product) {
		Optional<Product> optional = productRepository.findById(id);
		
		if(optional.isPresent()) {
			Product result = optional.get();
			
			result.setProductName(product.getProductName());
			result.setSubcategoryId(product.getSubcategoryId());
			result.setPrice(product.getPrice());
			result.setStock(product.getStock());
			result.setSize(product.getSize());
			result.setColor(product.getColor());
			result.setProductDescription(product.getProductDescription());
			result.setOnSale(product.getOnSale());
			result.setPimages(product.getPimages());
			
			return result;
		}
		return null;
		
	}
	
//	刪除商品
	public void deleteProduct(Integer id) {
		productRepository.deleteById(id);
	}
	
//	查詢單筆商品
	public Product findProductById(Integer id) {
		Optional<Product> optional = productRepository.findById(id);
		
		if(optional.isPresent()) {
			
			return optional.get();
		}
		
		return null;
	}
	
																					//	查詢全部商品
																					//	public List<Product> findAllProduct() {
																					//		return productRepository.findAll();
																					//	}
	
//	模糊搜尋 && 價格由高到低||由低到高 && 全部商品
	 public List<Product> findProductsByNameAndSort(String name, String sort) {
	        return productRepository.findByNameAndSort(name, sort);
	    }
	
}
