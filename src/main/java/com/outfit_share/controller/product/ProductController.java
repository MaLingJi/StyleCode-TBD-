package com.outfit_share.controller.product;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.outfit_share.entity.product.Product;
import com.outfit_share.service.product.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 創建新商品
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    // 更新商品
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }
    
    // 刪除商品
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        
        return "刪除ok";
    }

    // 獲取單個商品
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Integer id) {
        return productService.findProductById(id);
    }
    
    // 處理商品購買
    @PostMapping("/{id}/purchase")
    public ResponseEntity<?> purchaseProduct(@PathVariable Integer id, @RequestParam Integer quantity) {
        try {
            Product updatedProduct = productService.purchaseProduct(id, quantity);
            return ResponseEntity.ok(updatedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

																					    // 獲取所有商品
																					//    @GetMapping
																					//    public List<Product> getAllProducts() {
																					//        return productService.findAllProduct();
																					//    }
    
    
    // 模糊搜尋 && 價格由高到低||由低到高 && 全部商品
    // 只按名稱搜尋：/api/products?name=某商品名  
    // 僅按價格排序：
    // 從低到高：/api/products?sort=priceAsc
    // 從高到低：/api/products?sort=priceDesc
    // 同時按名稱搜尋和價格排序：/api/products?name=某商品名&sort=priceAsc
    // 如果既不提供名稱也不提供排序，則會傳回所有商品，不進行排序。
    @GetMapping 
    public List<Product> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "") String sort) {
        return productService.findProductsByNameAndSort(name, sort);
    }
    
}