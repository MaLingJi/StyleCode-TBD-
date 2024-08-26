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
import com.outfit_share.repository.product.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	private PimagesService pimagesService;

// 	檢查庫存並更新商品狀態
	private Product checkStockAndUpdateStatus(Product product) {
		if (product.getStock() <= 0) {
			product.setOnSale(false);
		}
		return product;
	}

//	新增商品
	public ProductDTO saveProduct(Product product) {
		Product saveProduct = productRepository.save(product);
		return new ProductDTO(saveProduct);
	}


//	新增商品
//	public ProductDTO saveProductWithImages(Product product, MultipartFile[] file, String imageType)
//			throws IOException {
//		// 保存商品
//		Product savedProduct = productRepository.save(product);
//
//		// 上傳並保存
//		List<PimagesDTO> savedImages = pimagesService.saveMultiplePimages(file, savedProduct.getProductId(), imageType);
//		List<Pimages> updatedPimages = new ArrayList<>();
//
//		if (savedImages != null) {
//			for (PimagesDTO dto : savedImages) {
//				if (dto != null) {
//					Pimages pimage = new Pimages();
//					pimage.setImageId(dto.getImageId());
//					pimage.setImageName(dto.getImageName());
//					pimage.setImgUrl(dto.getImgUrl());
//					pimage.setImageType(dto.getImageType());
//					pimage.setProductId(savedProduct);
//					updatedPimages.add(pimage);
//				}
//			}
//		}
//		savedProduct.setPimages(updatedPimages);
//
//		// 再次保存商品以更新圖片關聯
//		savedProduct = productRepository.save(savedProduct);
//
//		return new ProductDTO(savedProduct);
//	}

//	修改商品
	public ProductDTO updateProduct(Integer id, Product product) {
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
			if (product.getStock() != null) {
				result.setStock(product.getStock());
			}
			if (product.getSize() != null) {
				result.setSize(product.getSize());
			}
			if (product.getColor() != null) {
				result.setColor(product.getColor());
			}
			if (product.getProductDescription() != null) {
				result.setProductDescription(product.getProductDescription());
			}

			// 檢查庫存並更新狀態
			result = checkStockAndUpdateStatus(result);

			Product updatedProduct = productRepository.save(result);
			return new ProductDTO(updatedProduct);
		}

		return null;
	}

//	修改商品(可同時修改照片)
//	public ProductDTO updateProductWithImages(Integer id, Product product, MultipartFile[] file,
//			List<Integer> deleteImageIds, String imageType) throws IOException {
//		Optional<Product> optional = productRepository.findById(id);
//
//		if (optional.isPresent()) {
//			Product result = optional.get();
//
//			// 更新產品基本訊息
//			result.setProductName(product.getProductName());
//			result.setPrice(product.getPrice());
//			result.setStock(product.getStock());
//			result.setSize(product.getSize());
//			result.setColor(product.getColor());
//			result.setProductDescription(product.getProductDescription());
//			result.setSubcategoryId(product.getSubcategoryId());
//
//			// 檢查庫存並更新狀態
//			result = checkStockAndUpdateStatus(result);
//
//			// 删除指定的圖片(可以多張)
//			if (deleteImageIds != null && !deleteImageIds.isEmpty()) {
//				result.getPimages().removeIf(image -> deleteImageIds.contains(image.getImageId()));
//				for (Integer imageId : deleteImageIds) {
//					pimagesService.deletePimages(imageId);
//				}
//			}
//
//			// 添加新圖片
//			if (file != null && file.length > 0) {
//				List<PimagesDTO> savedImages = pimagesService.saveMultiplePimages(file,
//						result.getProductId(), imageType);
//				for (PimagesDTO dto : savedImages) {
//					if (dto != null) {
//						Pimages pimage = new Pimages();
//						pimage.setImageId(dto.getImageId());
//						pimage.setImageName(dto.getImageName());
//						pimage.setImgUrl(dto.getImgUrl());
//						pimage.setImageType(dto.getImageType());
//						pimage.setProductId(result);
//						result.getPimages().add(pimage);
//					}
//				}
//			}
//
//			// 保存更新後的產品
//			Product updatedProduct = productRepository.save(result);
//			return new ProductDTO(updatedProduct);
//		}
//
//		return null;
//	}

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
	
    //    查詢所有商品
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
        for(Product product : products) {
        	productDTO.add(new ProductDTO(product));
        }
        return productDTO;
        
    }
    
//  搜尋分類底下的所有商品
    public List<ProductDTO> findProductsByCategoryId(Integer categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        
        //更簡潔的語法
//        return products.stream().map(ProductDTO::new).collect(Collectors.toList());
        
        List<ProductDTO> productDTO= new ArrayList<>();
        
        for(Product product : products) {
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
