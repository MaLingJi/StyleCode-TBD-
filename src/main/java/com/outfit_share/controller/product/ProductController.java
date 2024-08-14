package com.outfit_share.controller.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.outfit_share.entity.product.Product;
import com.outfit_share.service.product.ProductService;

@RestController
//@RequestMapping("/api/products")
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
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }

    // 獲取單個商品
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Integer id) {
        return productService.findProductById(id);
    }

    // 獲取所有商品
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAllProduct();
    }
}