package com.outfit_share.controller.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.outfit_share.entity.product.Subcategory;
import com.outfit_share.entity.product.SubcategoryDTO;
import com.outfit_share.service.product.SubcategoryService;

@RestController
//@RequestMapping("/subcategories")
public class SubcategoryController {

    @Autowired
    private SubcategoryService subcategoryService;

    // 創建新子分類
    @PostMapping("/admin/subcategories/create")
    public SubcategoryDTO createSubcategory(@RequestBody Subcategory subcategory) {
        return subcategoryService.saveSubcategory(subcategory);
    }

    // 更新子分類
    @PutMapping("/admin/subcategories/{id}")
    public SubcategoryDTO updateSubcategory(@PathVariable("id") Integer id, @RequestBody Subcategory subcategory ) {
        return subcategoryService.updateSubcategory(id, subcategory);
    }

    // 刪除子分類
    @DeleteMapping("/admin/subcategories/{id}")
    public SubcategoryDTO deleteSubcategory(@PathVariable("id") Integer id) {
    	return subcategoryService.deleteSubcategory(id);
    }

    // 獲取單個子分類
    @GetMapping("/subcategories/{id}")
    public SubcategoryDTO getSubcategory(@PathVariable("id") Integer id) {
        return subcategoryService.findSubcategoryById(id);
    }
    
    // 新增: 通過父分類ID獲取子分類列表
    @GetMapping("/subcategories/categories/{id}")
    public List<SubcategoryDTO> getSubcategoriesByCategoryId(@PathVariable("id") Integer categoryId) {
        return subcategoryService.findSubcategoriesByCategoryId(categoryId);
    }
}