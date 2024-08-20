package com.outfit_share.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.product.Categories;
import com.outfit_share.entity.product.CategoriesDTO;
import com.outfit_share.repository.product.CategoriesRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class CategoriesService {

	@Autowired
	private CategoriesRepository categoriesRepository;

//	新增分類選項
	public CategoriesDTO saveCategories(Categories categories) {
		Categories savedCategory = categoriesRepository.save(categories);
		return new CategoriesDTO(savedCategory);
	}

//	修改分類名稱
	public CategoriesDTO updateCategories(Integer id, String updateCategories) {
		Optional<Categories> optional = categoriesRepository.findById(id);

		if (optional.isPresent()) {
			Categories result = optional.get();
			result.setCategoryName(updateCategories);
			Categories updatedCategory = categoriesRepository.save(result);
			return new CategoriesDTO(updatedCategory);
		}
		return null;
	}

//	刪除分類
	public CategoriesDTO deleteCategories(Integer id) {
		Optional<Categories> optional = categoriesRepository.findById(id);
		
		if (optional.isPresent()) {
			Categories categories = optional.get();
			CategoriesDTO categoriesDTO = new CategoriesDTO(categories);
			categoriesRepository.deleteById(id);
			return categoriesDTO;
		}
		return null;
	}

//	查詢分類
	public CategoriesDTO  findCategoriesById(Integer id) {
		Optional<Categories> optional = categoriesRepository.findById(id);

		if (optional.isPresent()) {
			Categories categories = optional.get();
			CategoriesDTO categoriesDTO = new CategoriesDTO(categories);
			return categoriesDTO;
		}

		return null;
	}
	
//  獲取全部分類
	public List<CategoriesDTO> findAllCategories(){
		List<Categories> list = categoriesRepository.findAll();
		List<CategoriesDTO> dtolist = new ArrayList<>();
		
		for(Categories categories : list) {
			Hibernate.initialize(categories.getCategoryId());
			CategoriesDTO categoriesDTO = new CategoriesDTO(categories);
			dtolist.add(categoriesDTO);
		}
		return dtolist;
	}
}
