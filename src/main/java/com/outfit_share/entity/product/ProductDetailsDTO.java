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
    
    private Integer stock;
    
    private String size;
    
    private String color;
    
    private Boolean onSale;
    
    
    private Integer productId;// 從 Product 實體獲取
    
    private String productName;  // 從 Product 實體獲取
    
    private String productDescription;// 從 Product 實體獲取
    
    private Integer price;// 從 Product 實體獲取
    

    public ProductDetailsDTO(ProductDetails productDetails) {
        this.productDetailsId = productDetails.getProductDetailsId();
        this.stock = productDetails.getStock();
        this.size = productDetails.getSize();
        this.color = productDetails.getColor();
        this.onSale = productDetails.getOnSale();
        
        
        this.productId = productDetails.getProductId().getProductId();
        this.productName = productDetails.getProductId().getProductName();
        this.productDescription = productDetails.getProductId().getProductDescription();
        this.price = productDetails.getProductId().getPrice();
    }
}