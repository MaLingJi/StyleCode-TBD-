package com.outfit_share.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.product.Subcategory;
import com.outfit_share.entity.product.SubcategoryDTO;
import com.outfit_share.repository.product.SubcategoryRepository;

import jakarta.transaction.Transactional;
import lombok.Setter;

@Service
@Transactional
public class SubcategoryService {

	
	@Autowired
	private SubcategoryRepository subcategoryRepository;
	
//	新增子分類
	public SubcategoryDTO saveSubcategory(Subcategory subcategory){
		Subcategory result = subcategoryRepository.save(subcategory);
		return new SubcategoryDTO(result);
		
	}
	
//	修改子分類
	public SubcategoryDTO updateSubcategory(Integer id ,Subcategory subcategory) {
		Optional<Subcategory> optional = subcategoryRepository.findById(id);
		
		if(optional.isPresent()) {
			Subcategory result = optional.get();
			result.setCategory(subcategory.getCategory());
			result.setSubcategoryName(subcategory.getSubcategoryName());
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
	
}
