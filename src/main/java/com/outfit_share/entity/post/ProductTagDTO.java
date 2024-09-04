package com.outfit_share.entity.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductTagDTO {
    private Integer id;
    private Integer postId;
    private Integer subcategoryId;
    private String productName;

    public ProductTagDTO(ProductTag productTag) {
        this.id = productTag.getId();
        this.postId = productTag.getPost().getPostId();
        this.subcategoryId = productTag.getSubcategory().getSubcategoryId();
        this.productName = productTag.getProductName();
    }
}
