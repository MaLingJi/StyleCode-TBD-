package com.outfit_share.entity.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    
    private Integer productId;
    private String productName;
    private Integer subcategoryId; // 只包含ID，而不是整個Subcategory對象
    private Integer price;
    private Integer stock;
    private String size;
    private String color;
    private String productDescription;
    private boolean onSale;
    
    // 構造函數
    public ProductDTO(Product product) {
        this.productId = productId;
        this.productName = productName;
        this.subcategoryId = subcategoryId;
        this.price = price;
        this.stock = stock;
        this.size = size;
        this.color = color;
        this.productDescription = productDescription;
        this.onSale = onSale;
    }
}