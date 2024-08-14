package com.outfit_share.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.outfit_share.entity.product.Categories;
import com.outfit_share.service.product.CategoriesService;

@RestController
//@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    // 創建新分類
    @PostMapping
    public Categories createCategory(@RequestBody Categories category) {
        return categoriesService.saveCategories(category);
    }

    // 更新分類名稱
    @PutMapping("/{id}")
    public Categories updateCategory(@PathVariable Integer id, @RequestBody String categoryName) {
        return categoriesService.updateCategories(id, categoryName);
    }

    // 刪除分類
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoriesService.deleteCategories(id);
    }

    // 獲取單個分類
    @GetMapping("/{id}")
    public Categories getCategory(@PathVariable Integer id) {
        return categoriesService.findCategoriesById(id);
    }
}