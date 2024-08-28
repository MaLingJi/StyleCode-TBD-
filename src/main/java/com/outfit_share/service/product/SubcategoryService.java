package com.outfit_share.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.product.Categories;
import com.outfit_share.entity.product.Subcategory;
import com.outfit_share.entity.product.SubcategoryDTO;
import com.outfit_share.repository.product.CategoriesRepository;
import com.outfit_share.repository.product.SubcategoryRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SubcategoryService {

	
	@Autowired
	private SubcategoryRepository subcategoryRepository;
	
	@Autowired
	private CategoriesRepository categoriesRepository;
	
//	新增子分類
	public SubcategoryDTO saveSubcategory(Subcategory subcategory){
		
		 if (subcategory.getCategory() == null || subcategory.getCategory().getCategoryId() == null) {
	            throw new IllegalArgumentException("Category must be set");
	        }
		 Categories category = categoriesRepository.findById(subcategory.getCategory().getCategoryId())
		            .orElseThrow(() -> new EntityNotFoundException("Category not found"));
		 
		subcategory.setCategory(category);
		
		Subcategory result = subcategoryRepository.save(subcategory);
		return new SubcategoryDTO(result);
		
		
	}
	
//	修改子分類
	public SubcategoryDTO updateSubcategory(Integer id ,Subcategory subcategory) {
		Optional<Subcategory> optional = subcategoryRepository.findById(id);
		
		if(optional.isPresent()) {
			Subcategory result = optional.get();
			
			result.setSubcategoryName(subcategory.getSubcategoryName());
			
			Subcategory updatedsubcategory = subcategoryRepository.save(result);
			return new SubcategoryDTO(updatedsubcategory);
		}
		return null;
	}
	
//	刪除子分類
	public SubcategoryDTO deleteSubcategory(Integer id) {
		Optional<Subcategory> optional = subcategoryRepository.findById(id);
		
		if (optional.isPresent()) {
			
			Subcategory subcategory = optional.get();
			SubcategoryDTO subcategoryDTO = new SubcategoryDTO(subcategory);
			subcategoryRepository.deleteById(id); 
			return subcategoryDTO;
		}
		return null;
	}
	
//	搜尋子分類
	public SubcategoryDTO findSubcategoryById(Integer id) {
		 Optional<Subcategory> optional = subcategoryRepository.findById(id);
		 
		 //更簡潔的寫法
//		 return optional.map(SubcategoryDTO::new).orElse(null);
		 if(optional.isPresent()) {
			 
			 Subcategory subcategory = optional.get();
			 return new SubcategoryDTO(subcategory);
		 }
		 return null;
	}
	
	
	 // 新增: 通過父分類ID獲取子分類列表
	 public List<SubcategoryDTO> findSubcategoriesByCategoryId(Integer categoryId) {
	        List<Subcategory> list = subcategoryRepository.findByCategoryCategoryId(categoryId);
	        List<SubcategoryDTO> dtolist = new ArrayList<>();
	        
	        for(Subcategory subcategory : list) {
				Hibernate.initialize(subcategory.getCategory());
				SubcategoryDTO subcategoryDTO = new SubcategoryDTO(subcategory);
				dtolist.add(subcategoryDTO);
	    }
	        return dtolist;
	 }
	
	 //搜尋全部子分類
	 public List<SubcategoryDTO> findAllSubcategories(){
		 
		 List<Subcategory> list = subcategoryRepository.findAll();
		 List<SubcategoryDTO> dtolist = new ArrayList<>();
		 
		 for(Subcategory subcategory : list) {
			 Hibernate.initialize(subcategory.getSubcategoryId());
			 SubcategoryDTO dto = new SubcategoryDTO(subcategory);
			 dtolist.add(dto);
		 }
		 return dtolist;
	 }
}
