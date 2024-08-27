package com.outfit_share.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.product.Product;
import com.outfit_share.entity.product.ProductDTO;
import com.outfit_share.entity.product.ProductDetails;
import com.outfit_share.repository.product.ProductDetailsRepository;
import com.outfit_share.repository.product.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductDetailsRepository productDetailsRepository;


// 	檢查庫存並更新商品狀態
	private ProductDetails checkStockAndUpdateStatus(ProductDetails details) {
		if (details.getStock() <= 0) {
			details.setOnSale(false);
		}
		return details;
	}

//	新增商品
	  public ProductDTO saveProduct(Product product, List<ProductDetails> details) {
		  
	        if (details != null) {
	            List<ProductDetails> updatedDetails = new ArrayList<>();
	            for (ProductDetails detail : details) {
	                detail.setProductId(product);
	                checkStockAndUpdateStatus(detail);
	                updatedDetails.add(detail);
	            }
	            product.setProductDetails(updatedDetails);
	        }
	        
	        Product savedProduct = productRepository.save(product);
	        return new ProductDTO(savedProduct);
	    }
	  
//	  在已有的商品編號底下 可以新增其他商品 例:可以新增 其他顏色和尺寸
	  public ProductDTO addProductDetails(Integer productId, List<ProductDetails> newDetails) {
		    Optional<Product> optionalProduct = productRepository.findById(productId);
		    
		    if (optionalProduct.isPresent()) {
		        Product product = optionalProduct.get();
		        
		        if (newDetails != null) {
		            for (ProductDetails detail : newDetails) {
		                detail.setProductId(product);
		                checkStockAndUpdateStatus(detail);
		                product.addProductDetail(detail);
		            }
		        }
		        
		        Product updatedProduct = productRepository.save(product);
		        return new ProductDTO(updatedProduct);
		    }
		    
		    throw new IllegalArgumentException("商品不存在");
		}

//	修改商品
	public ProductDTO updateProduct(Integer id, Product product, List<ProductDetails> details) {
		Optional<Product> optional = productRepository.findById(id);

		if (optional.isPresent()) {
			Product result = optional.get();

			// 只更新非null的字段
			if (product.getProductName() != null) {
				result.setProductName(product.getProductName());
			}
			if (product.getPrice() != null) {
				result.setPrice(product.getPrice());
			}

			if (product.getProductDescription() != null) {
				result.setProductDescription(product.getProductDescription());
			}
			productRepository.save(result);

			if (details != null) {

				for (ProductDetails updatedDetail : details) {
					Optional<ProductDetails> existingDetailOpt = productDetailsRepository
							.findById(updatedDetail.getProductDetailsId());
					
				if (existingDetailOpt.isPresent()) {
					 ProductDetails existingDetail = existingDetailOpt.get();
					 
				if (updatedDetail.getStock() != null) {
					existingDetail.setStock(updatedDetail.getStock());
				}
				if (updatedDetail.getSize() != null) {
					existingDetail.setSize(updatedDetail.getSize());
				}
				if (updatedDetail.getColor() != null) {
					existingDetail.setColor(updatedDetail.getColor());
				}

				// 檢查庫存並更新狀態
				productDetailsRepository.save(checkStockAndUpdateStatus(existingDetail));

				}
				
				}
			}
			return new ProductDTO(result);
		}

		return null;
	}

//  處理商品購買
	public ProductDTO purchaseProduct(Integer productId, Integer detailId, Integer quantity) {
		Optional<Product> optional = productRepository.findById(productId);
		 Optional<ProductDetails> detailOpt = productDetailsRepository.findById(detailId);
		 
		if (optional.isPresent() && detailOpt.isPresent()) {
			Product product = optional.get();
			ProductDetails detail = detailOpt.get();
			
			 if (!detail.isOnSale()) {
	                throw new IllegalStateException("此商品目前不可購買");
	            }
			 
			int newStock = detail.getStock() - quantity;

			if (newStock < 0) {
				throw new IllegalArgumentException("庫存不足");
			}

			detail.setStock(newStock);
			ProductDetails updatedDetail =  checkStockAndUpdateStatus(detail);
			productDetailsRepository.save(updatedDetail);
			
			return new ProductDTO(product);
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
	
//	查詢所有商品
	public List<ProductDTO> findAllProduct(){
		List<Product> list = productRepository.findAll();
		List<ProductDTO> dtolist = new ArrayList<>();
		
		for(Product product : list) {
			Hibernate.initialize(product.getProductId());
			ProductDTO productDTO = new ProductDTO(product);
			dtolist.add(productDTO);
		}
		return dtolist;
	}
	
//	搜尋子分類底下的所有商品
	public List<ProductDTO> findProductsBySubcategoryId(Integer subcategoryId) {
		List<Product> products = productRepository.findBySubcategoryId(subcategoryId);

		// 更簡潔的語法
//        return products.stream().map(ProductDTO::new).collect(Collectors.toList());

		List<ProductDTO> productDTO = new ArrayList<>();
		for (Product product : products) {
			productDTO.add(new ProductDTO(product));
		}
		return productDTO;

	}

//  搜尋分類底下的所有商品
	public List<ProductDTO> findProductsByCategoryId(Integer categoryId) {
		List<Product> products = productRepository.findByCategoryId(categoryId);

		// 更簡潔的語法
//        return products.stream().map(ProductDTO::new).collect(Collectors.toList());

		List<ProductDTO> productDTO = new ArrayList<>();

		for (Product product : products) {
			productDTO.add(new ProductDTO(product));
		}
		return productDTO;
	}

	// 新增: 通过分类ID或子分类ID查找商品
	public List<ProductDTO> findProductsByCategoryOrSubcategory(Integer categoryId, Integer subcategoryId) {
		List<Product> products;
		if (subcategoryId != null) {
			products = productRepository.findBySubcategoryId(subcategoryId);
		} else if (categoryId != null) {
			products = productRepository.findByCategoryId(categoryId);
		} else {
			products = productRepository.findAll();
		}
		return products.stream().map(ProductDTO::new).collect(Collectors.toList());
	}

    
    
//	模糊搜尋 && 價格由高到低||由低到高 && 全部商品
	public List<ProductDTO> findProductsByNameAndSort(String name, String sort) {
		List<Product> products = productRepository.findByNameAndSort(name, sort);
		List<ProductDTO> productDTOs = new ArrayList<>();

		// 更簡潔的寫法
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
