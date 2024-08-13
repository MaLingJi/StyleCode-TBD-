package com.outfit_share.service.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.product.Categories;
import com.outfit_share.repository.product.CategoriesRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class CategoriesService {

	@Autowired
	private CategoriesRepository categoriesRepository;
	
//	新增分類選項
	public Categories saveCategories (Categories categories) {
		return categoriesRepository.save(categories);
	}
	
	
//	修改分類名稱
	public Categories updateCategories(Integer id, String updateCategories){
		Optional<Categories> optional = categoriesRepository.findById(id);
		
		if(optional.isPresent()) {
			Categories result = optional.get();
			result.setCategoryName(updateCategories);
		}
		return null;
	}
	
//	刪除分類
	public void deleteCategories(Integer id) {
		categoriesRepository.deleteById(id);
	}
	
//	查詢分類
	public Categories findCategoriesById(Integer id) {
		Optional<Categories> optional = categoriesRepository.findById(id);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		
		return null;
	}
}
