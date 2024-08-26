package com.outfit_share.entity.product;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDetailsDTO {

    private Integer productDetailsId;
    
    private Integer productId;
    
    private String productName;  // 從 Product 實體獲取
    
    private Integer price;
    
    private Integer stock;
    
    private String size;
    
    private String color;
    
    private String productDescription;
    
    private boolean onSale;
    

    public ProductDetailsDTO(ProductDetails productDetails) {
        this.productDetailsId = productDetails.getProductDetailsId();
        this.productId = productDetails.getProductId().getProductId();
        this.productName = productDetails.getProductId().getProductName();
        this.price = productDetails.getPrice();
        this.stock = productDetails.getStock();
        this.size = productDetails.getSize();
        this.color = productDetails.getColor();
        this.productDescription = productDetails.getProductDescription();
        this.onSale = productDetails.isOnSale();
        
    }
}