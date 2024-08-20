package com.outfit_share.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.product.Product;
import com.outfit_share.entity.product.ProductDTO;
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
	public ProductDTO saveProduct(Product product) {
		Product savedProduct = productRepository.save(product);
		return new ProductDTO(savedProduct);
	}

//	修改商品
	public ProductDTO updateProduct(Integer id, Product product) {
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
			result.setSubcategoryId(product.getSubcategoryId());
			// 檢查庫存並更新狀態
			result = checkStockAndUpdateStatus(result);

			Product updatedProduct = productRepository.save(result);
			return new ProductDTO(updatedProduct);
		}

		return null;

	}

//  處理商品購買
	public ProductDTO purchaseProduct(Integer id, Integer quantity) {
		Optional<Product> optional = productRepository.findById(id);

		if (optional.isPresent()) {
			Product product = optional.get();
			int newStock = product.getStock() - quantity;

			if (newStock < 0) {
				throw new IllegalArgumentException("庫存不足");
			}

			product.setStock(newStock);
			product = checkStockAndUpdateStatus(product);

			Product updatedProduct = productRepository.save(product);
			return new ProductDTO(updatedProduct);
		}
		throw new IllegalArgumentException("商品不存在");
	}

//	刪除商品
	public ProductDTO deleteProduct(Integer id) {
		Optional<Product> optional = productRepository.findById(id);

		if (optional.isPresent()) {
			Product product = optional.get();
			ProductDTO productDTO = new ProductDTO(product);
			productRepository.deleteById(id);
			return productDTO;
		}
		return null;
	}

//	查詢單筆商品
	public ProductDTO findProductById(Integer id) {
		Optional<Product> optional = productRepository.findById(id);

		if (optional.isPresent()) {

			Product product = optional.get();
			return new ProductDTO(product);
		}

		return null;
	}

	// 查詢全部商品
	// public List<Product> findAllProduct() {
	// return productRepository.findAll();
	// }

//	模糊搜尋 && 價格由高到低||由低到高 && 全部商品
	public List<ProductDTO> findProductsByNameAndSort(String name, String sort) {
		List<Product> products = productRepository.findByNameAndSort(name, sort);
		List<ProductDTO> productDTOs = new ArrayList<>();

		//更簡潔的寫法
//		return products.stream().map(ProductDTO::new).collect(Collectors.toList());
		
		if (products != null && !products.isEmpty()) {
			for (Product product : products) {
				if (product != null) {
					ProductDTO productDTO = new ProductDTO(product);
					productDTOs.add(productDTO);
				}
			}
		}

		return productDTOs;
	}

}
