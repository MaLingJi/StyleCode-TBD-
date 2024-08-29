package com.outfit_share.controller.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outfit_share.entity.product.Categories;
import com.outfit_share.entity.product.CategoriesDTO;
import com.outfit_share.service.product.CategoriesService;

@RestController
//@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    // 創建新分類
    @PostMapping("/admin/categories/create")
    public CategoriesDTO createCategories(@RequestBody Categories category) {
        return categoriesService.saveCategories(category);
    }

    // 更新分類名稱
    @PutMapping("/admin/categories/{id}")
    public CategoriesDTO updateCategoriesById(@PathVariable Integer id, @RequestBody Categories category) {
        return categoriesService.updateCategories(id, category.getCategoryName());
    }

    // 刪除分類
    @DeleteMapping("/admin/categories/{id}")
    public CategoriesDTO deleteCategoriesById(@PathVariable Integer id) {
        return categoriesService.deleteCategories(id);
    }

    // 獲取單個分類
    @GetMapping("/categories/{id}")
    public CategoriesDTO getCategoriesById(@PathVariable Integer id) {
        return categoriesService.findCategoriesById(id);
    }
    
    // 獲取全部分類
    @GetMapping("/categories")
    public List<CategoriesDTO> getAllCategories(){
    	return categoriesService.findAllCategories();
    	
    	
    }
}