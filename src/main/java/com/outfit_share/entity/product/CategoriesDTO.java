package com.outfit_share.entity.product;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriesDTO {

    private Integer categoryId; // 分類ID
    
    private String categoryName; // 分類名稱
    
    private List<SubcategoryDTO> subcategories; // 分類中的子分類列表
    
    public CategoriesDTO(Categories categories) {
        this.categoryId = categories.getCategoryId();
        this.categoryName = categories.getCategoryName();
        
        //比較簡潔的語法
//        if (categories.getSubcategories() != null) {
//            this.subcategories = categories.getSubcategories().stream()
//                .map(SubcategoryDTO::new)
//                .collect(Collectors.toList());
//        }
        
        if(categories.getSubcategories() != null) {
        	this.subcategories = new ArrayList<>();
        	
        	for(Subcategory subcategory : categories.getSubcategories()) {
        		if(subcategory != null) {
        			this.subcategories.add(new SubcategoryDTO(subcategory));
        		}
        	}
        }
    }
}