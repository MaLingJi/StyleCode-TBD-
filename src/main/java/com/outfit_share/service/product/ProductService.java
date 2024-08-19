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

// 	檢查庫存並更新商品狀態
	private Product checkStockAndUpdateStatus(Product product) {
		if (product.getStock() <= 0) {
			product.setOnSale(false);
		}
		return product;
	}

//	新增商品
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

//	修改商品
	public Product updateProduct(Integer id, Product product) {
		Optional<Product> optional = productRepository.findById(id);

		if (optional.isPresent()) {
			Product result = optional.get();

			result.setProductName(product.getProductName());
			result.setPrice(product.getPrice());
			result.setStock(product.getStock());
			result.setSize(product.getSize());
			result.setColor(product.getColor());
			result.setProductDescription(product.getProductDescription());
			result.setPimages(product.getPimages());
			// 檢查庫存並更新狀態
			result = checkStockAndUpdateStatus(result);

			return productRepository.save(result);
		}
		return null;

	}

//  處理商品購買
	public Product purchaseProduct(Integer id, Integer quantity) {
		Optional<Product> optional = productRepository.findById(id);

		if (optional.isPresent()) {
			Product product = optional.get();
			int newStock = product.getStock() - quantity;

			if (newStock < 0) {
				throw new IllegalArgumentException("庫存不足");
			}

			product.setStock(newStock);
			product = checkStockAndUpdateStatus(product);

			return productRepository.save(product);
		}
		throw new IllegalArgumentException("商品不存在");
	}

//	刪除商品
	public void deleteProduct(Integer id) {
		productRepository.deleteById(id);
	}

//	查詢單筆商品
	public Product findProductById(Integer id) {
		Optional<Product> optional = productRepository.findById(id);

		if (optional.isPresent()) {

			return optional.get();
		}

		return null;
	}

	// 查詢全部商品
	// public List<Product> findAllProduct() {
	// return productRepository.findAll();
	// }

//	模糊搜尋 && 價格由高到低||由低到高 && 全部商品
	public List<Product> findProductsByNameAndSort(String name, String sort) {
		return productRepository.findByNameAndSort(name, sort);
	}

}
