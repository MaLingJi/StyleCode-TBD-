package com.outfit_share.controller.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.outfit_share.entity.product.Product;
import com.outfit_share.entity.product.ProductDTO;
import com.outfit_share.entity.product.ProductDetails;
import com.outfit_share.entity.product.ProductDetailsDTO;
import com.outfit_share.service.product.ProductService;

@RestController
//@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 新增商品
   @PostMapping("/admin/products/create")
   public ResponseEntity<ProductDTO> createProduct(@RequestBody Product product) {
       if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
           return ResponseEntity.badRequest().body(null);
       }
       ProductDTO createdProduct = productService.saveProduct(product, product.getProductDetails());
       return ResponseEntity.ok(createdProduct);
   }
   
   //在已有的商品編號底下 可以新增其他商品 例:可以新增 其他顏色和尺寸
   @PostMapping("/admin/{productId}/productDetails")
   public ResponseEntity<ProductDTO> addProductDetails(@PathVariable Integer productId, @RequestBody List<ProductDetails> newDetails) {
       ProductDTO updatedProduct = productService.addProductDetails(productId, newDetails);
       return ResponseEntity.ok(updatedProduct);
   }
    
    // 修改商品
    // 只更新商品資訊 /admin/products/{id}
    // 修改商品資訊並改變商品狀態
    @PutMapping("/admin/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
    	
          ProductDTO updateProduct = productService.updateProduct(id, product);
          if(updateProduct != null) {
        	  return ResponseEntity.ok(updateProduct);
          }
          										// 單純回傳 404
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    // 修改商品詳情 	
    //修改商品資訊並修改商品狀態 || 只改變商品狀態  /admin/productDetails{id}?onSale=true
    @PutMapping("/admin/productDetails/{id}")
    public ResponseEntity<?> updateDetails(@PathVariable Integer id ,@RequestBody ProductDetails details){
    	ProductDetailsDTO detailsDTO = productService.updateDetails(id, details);
    	if(detailsDTO != null) {
    		return ResponseEntity.ok(detailsDTO);
    	}
    	
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    

    // 刪除商品
    @DeleteMapping("/admin/products/{id}")
    public ProductDTO deleteProduct(@PathVariable Integer id) {
    	return productService.deleteProduct(id);
        
    }
    
    //刪除商品詳情
    @DeleteMapping("/admin/productDetails/{id}")
    public ProductDetailsDTO deleteDetails(@PathVariable Integer id) {
    	return productService.deleteDetails(id);
    }
    
    // 處理商品購買
    // 輸入的範例 /admin/products/{id}/details/{detailId}/purchase?quantity=1
    @PostMapping("/admin/products/{id}/details/{detailId}/purchase")
    public ResponseEntity<?> purchaseProduct(@PathVariable Integer id,
    		@PathVariable Integer detailId,
    		@RequestParam Integer quantity) {
    	
    	try {
    		ProductDTO updatedProduct = productService.purchaseProduct(id, detailId, quantity);
    		return ResponseEntity.ok(updatedProduct);
    	} catch (IllegalArgumentException | IllegalStateException  e) {
    		return ResponseEntity.badRequest().body(e.getMessage());
    	}
    }

    // 獲取單個商品
    @GetMapping("/products/{id}")
    public ProductDTO getProduct(@PathVariable Integer id) {
        return productService.findProductById(id);
    }
    
    //查詢所有商品
    @GetMapping("/allproducts")
    public List<ProductDTO> getAllProduct(){
    	return productService.findAllProduct();
    }
    
    //搜尋子分類底下的商品
    @GetMapping("/products/subcategory/{subcategoryId}")
    public List<ProductDTO> getProductsBySubcategoryId(@PathVariable Integer subcategoryId){
    	return productService.findProductsBySubcategoryId(subcategoryId);
    }
    
    //搜尋分類底下的所有商品
    @GetMapping("/products/category/{categoryId}")
    public List<ProductDTO> getAllProductsByCategoryId(@PathVariable Integer categoryId){
    	return productService.findProductsByCategoryId(categoryId);
    }
    
    
    //搜尋子分類底下的商品 || 搜尋分類底下的所有商品 || 全部商品 
    //按照分類搜尋商品/filter?categoryId=??
    //按照子分類搜尋商品/filter?subcategoryId=??
    //找尋分類底下的子分類中的商品 /filter?categoryId=??&subcategoryId=??
    @GetMapping("/products/filter")
    public ResponseEntity<List<ProductDTO>> filterProducts(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer subcategoryId) {
    	
        List<ProductDTO> products = productService.findProductsByCategoryOrSubcategory(categoryId, subcategoryId);
        return ResponseEntity.ok(products);
    }
    
    // 模糊搜尋端點
    @GetMapping("/products/search/{name}")
    public ResponseEntity<List<ProductDTO>> searchProductsByName(@PathVariable String name) {
        List<ProductDTO> products = productService.findProductsByName(name);
        return ResponseEntity.ok(products);
    }

    // 價格排序端點
    @GetMapping("/products/sort")
    public ResponseEntity<List<ProductDTO>> sortProductsByPrice(@RequestParam String direction) {
        if (!direction.equalsIgnoreCase("ASC") && !direction.equalsIgnoreCase("DESC")) {
            return ResponseEntity.badRequest().body(null);
        }
        List<ProductDTO> products = productService.findAllProductsSortedByPrice(direction.toUpperCase());
        return ResponseEntity.ok(products);
    }

    
 // 按分類和價格排序
    @GetMapping("/products/category/{categoryId}/sort")
    public ResponseEntity<List<ProductDTO>> sortProductsByCategoryAndPrice(
            @PathVariable Integer categoryId,
            @RequestParam String direction) {
        if (!direction.equalsIgnoreCase("ASC") && !direction.equalsIgnoreCase("DESC")) {
            return ResponseEntity.badRequest().body(null);
        }
        List<ProductDTO> products = productService.findProductsByCategoryIdSortedByPrice(categoryId, direction.toUpperCase());
        return ResponseEntity.ok(products);
    }

    // 按子分類和價格排序
    @GetMapping("/products/subcategory/{subcategoryId}/sort")
    public ResponseEntity<List<ProductDTO>> sortProductsBySubcategoryAndPrice(
            @PathVariable Integer subcategoryId,
            @RequestParam String direction) {
        if (!direction.equalsIgnoreCase("ASC") && !direction.equalsIgnoreCase("DESC")) {
            return ResponseEntity.badRequest().body(null);
        }
        List<ProductDTO> products = productService.findProductsBySubcategoryIdSortedByPrice(subcategoryId, direction.toUpperCase());
        return ResponseEntity.ok(products);
    }
    
    
    
    // 模糊搜尋 && 價格由高到低 || 由低到高 && 全部商品
    // 只按名稱搜尋：/products?name=某商品名  
    // 僅按價格排序：
    // 從低到高：/products?sort=priceAsc
    // 從高到低：/products?sort=priceDesc
    // 同時按名稱搜尋和價格排序：/products?name=某商品名&sort=priceAsc
    // 如果既不提供名稱也不提供排序，則會傳回所有商品，不進行排序。
    @GetMapping("/products")
    public List<ProductDTO> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "") String sort) {
    	
        return productService.findProductsByNameAndSort(name, sort);
    }
    
}