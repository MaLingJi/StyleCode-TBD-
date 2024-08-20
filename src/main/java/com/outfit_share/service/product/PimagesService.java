package com.outfit_share.service.product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.outfit_share.entity.product.Pimages;
import com.outfit_share.entity.product.PimagesDTO;
import com.outfit_share.entity.product.Product;
import com.outfit_share.repository.product.PimagesRepository;
import com.outfit_share.repository.product.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PimagesService {

	@Value("${upload.dir}")
	private String uploadDir;

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private PimagesRepository pimagesRepository;

//	新增圖片 , 
//	String imageType ， pimages.setImageType(imageType);，有滑鼠移入移出事件測試，切換不同的圖片
	public PimagesDTO savePimages(MultipartFile file, Integer id) throws IOException {
		Product product = productRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("ProductPhoto not found with id: " + id));

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

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
		

		Pimages savedPimages = pimagesRepository.save(pimages);
		return new PimagesDTO(savedPimages);
	}

//  上傳多個圖片 ,  
//								有滑鼠移入移出事件測試，切換不同的圖片								//	String imageType , 有滑鼠移入移出事件測試，切換不同的圖片	
	public List<PimagesDTO> saveMultiplePimages(MultipartFile[] files, Integer productId)
			throws IOException {
		List<PimagesDTO> savedImages = new ArrayList<>();
		for (MultipartFile file : files) {			//, imageType 有滑鼠移入移出事件測試，切換不同的圖片	
			savedImages.add(savePimages(file, productId));
		}
		return savedImages;
	}

//	修改圖片
	public PimagesDTO updatePimages(MultipartFile file, Integer imageId) throws IOException {
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

			Pimages updatedPimages = pimagesRepository.save(result);
			return new PimagesDTO(updatedPimages);
		}

		return null;
	}

//	刪除圖片
	public PimagesDTO deletePimages(Integer id) {
		Optional<Pimages> optional = pimagesRepository.findById(id);

		if (optional.isPresent()) {
			Pimages pimages = optional.get();
			PimagesDTO pimagesDTO = new PimagesDTO(pimages);
			pimagesRepository.deleteById(id);
			return pimagesDTO;

		}
		return null;
	}

//	搜尋單張圖片
	public PimagesDTO findPimagesById(Integer id) {
		Optional<Pimages> optional = pimagesRepository.findById(id);

		if (optional.isPresent()) {
			return new PimagesDTO(optional.get());
		}

		return null;
	}

//	搜尋該商品的全部圖片
	public List<PimagesDTO> findAllImagesByProductId(Integer productId) {
		List<Pimages> pimages = pimagesRepository.findByProductIdProductId(productId);
		List<PimagesDTO> pimagesDTOs = new ArrayList<>();
		for (Pimages pimage : pimages) {
			pimagesDTOs.add(new PimagesDTO(pimage));
		}
		return pimagesDTOs;
	}

////	搜尋該商品的全部圖片的另一種寫法，有滑鼠移入移出事件測試，切換不同的圖片
//	public List<PimagesDTO> findAllImagesByProductId(Integer productId) {
//		List<Pimages> pimages = pimagesRepository.findByProductIdOrderByImageType(productId);
//
//		// 更簡潔的語法
////	        return pimages.stream().map(PimagesDTO::new).collect(Collectors.toList());
//
//		List<PimagesDTO> pimagesDTOs = new ArrayList<>();
//		for (Pimages pimage : pimages) {
//			pimagesDTOs.add(new PimagesDTO(pimage));
//		}
//		return pimagesDTOs;
//	}

//	封面照
	public PimagesDTO findCoverPhoto(Integer productId) {
		Pimages coverPhoto = pimagesRepository.findTopOneProductImage(productId);
		return coverPhoto != null ? new PimagesDTO(coverPhoto) : null;
	}

}
