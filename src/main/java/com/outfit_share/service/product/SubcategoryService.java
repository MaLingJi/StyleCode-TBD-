package com.outfit_share.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.product.Subcategory;
import com.outfit_share.repository.product.SubcategoryRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SubcategoryService {

	
	@Autowired
	private SubcategoryRepository subcategoryRepository;
	
//	新增子分類
	public Subcategory saveSubcategory(Subcategory subcategory){
		return subcategoryRepository.save(subcategory);
		
	}
	
//	修改子分類
	public Subcategory updateSubcategory(Integer id ,String Subcategory) {
		Optional<Subcategory> optional = subcategoryRepository.findById(id);
		
		if(optional.isPresent()) {
			Subcategory result = optional.get();
			result.setSubcategoryName(Subcategory);
		}
		return null;
	}
	
//	刪除子分類
	public void deleteSubcategory(Integer id) {
		subcategoryRepository.deleteById(id);
	}
	
//	搜尋子分類
	public Subcategory findSubcategoryById(Integer id) {
		 Optional<Subcategory> optional = subcategoryRepository.findById(id);
		 
		 if(optional.isPresent()) {
			 
			 return optional.get();
		 }
		 
		 return null;
	}
	
	// 新增: 通過父分類ID獲取子分類列表
	public List<Subcategory> findSubcategoriesByCategoryId(Integer categoryId) {
        return subcategoryRepository.findByCategoryCategoryId(categoryId);
    }
	
}
