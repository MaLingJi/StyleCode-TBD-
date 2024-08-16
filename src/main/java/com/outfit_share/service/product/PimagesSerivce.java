package com.outfit_share.service.product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.outfit_share.entity.product.Pimages;
import com.outfit_share.entity.product.Product;
import com.outfit_share.repository.product.PimagesRepository;
import com.outfit_share.repository.product.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PimagesSerivce {

	@Value("${upload.dir}")
	private String uploadDir;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private PimagesRepository pimagesRepository;
	
//	新增圖片
	public Pimages savePimages(MultipartFile file,Integer id) throws IOException{
		Product product = productRepo.findById(id).orElseThrow(()-> new RuntimeException("ProductPhoto not found with id: " + id));
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		String uniqueFileName =  UUID.randomUUID().toString() + "_" + fileName;
		
		Path uploaPath = Paths.get(uploadDir);
		
		if (!Files.exists(uploaPath)) {
			Files.createDirectories(uploaPath);
		}
		
		Path filePath = uploaPath.resolve(uniqueFileName);
		
		file.transferTo(filePath.toFile());
		
		Pimages pimages = new Pimages();
		
		pimages.setImageName(fileName);
		pimages.setImgUrl(filePath.toString());
		pimages.setProductId(product);
		
		return pimagesRepository.save(pimages);
		
	}
	
//  上傳多個圖片
    public List<Pimages> saveMultiplePimages(MultipartFile[] files, Integer productId) throws IOException {
        List<Pimages> savedImages = new ArrayList<>();
        for (MultipartFile file : files) {
            savedImages.add(savePimages(file, productId));
        }
        return savedImages;
    }
	   
//	修改圖片
    public Pimages updatePimages(MultipartFile file, Integer imageId) throws IOException {
        Optional<Pimages> optional = pimagesRepository.findById(imageId);
        
        if (optional.isPresent()) {
            Pimages result = optional.get();
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
            
            Path uploadPath = Paths.get(uploadDir);
            Path filePath = uploadPath.resolve(uniqueFileName);
            file.transferTo(filePath.toFile());
            
            result.setImageName(fileName);
            result.setImgUrl(filePath.toString());
            
            return pimagesRepository.save(result);
        }
        
        return null;
    }
	
//	刪除圖片
	public void deletePimages (Integer id) {
		pimagesRepository.deleteById(id);
	}
	
//	搜尋單張圖片
	public Pimages findPimagesById(Integer id) {
		Optional<Pimages> optional = pimagesRepository.findById(id);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		
		return null;
	}
	
//	搜尋該商品的全部圖片
	public List<Pimages> findAllImagesByProductId(Integer productId) {
	    return pimagesRepository.findByProductIdProductId(productId);
	}
	
}
