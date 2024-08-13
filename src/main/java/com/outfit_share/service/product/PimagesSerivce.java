package com.outfit_share.service.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.product.Pimages;
import com.outfit_share.repository.product.PimagesRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PimagesSerivce {

	
	@Autowired
	PimagesRepository pimagesRepository;
	
//	新增圖片
	public Pimages savePimages(Pimages img) {
		return pimagesRepository.save(img);
		
		
	}
//	修改圖片
	public Pimages updatePimages(Integer id, Pimages img) {
		Optional<Pimages> optional = pimagesRepository.findById(id);
		
		if(optional.isPresent()) {
			Pimages result = optional.get();
			result.setImgUrl(img.getImgUrl());
			
			return result;
		}
		
		return null;
	}
	
//	刪除圖片
	public void deletePimages (Integer id) {
		pimagesRepository.deleteById(id);
	}
	
	
	public Pimages findPimagesById(Integer id) {
		Optional<Pimages> optional = pimagesRepository.findById(id);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		
		return null;
	}
}
