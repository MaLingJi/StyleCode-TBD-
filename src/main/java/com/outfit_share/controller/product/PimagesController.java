package com.outfit_share.controller.product;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.outfit_share.entity.product.PimagesDTO;
import com.outfit_share.service.product.PimagesService;

@RestController
//@RequestMapping("/products/images")
public class PimagesController {

	@Autowired
	private PimagesService pimagesService;

																													// 上傳新圖片
																												    @PostMapping("/admin/products/images/{productId}/create")
																												    public PimagesDTO uploadImage(@RequestParam("file") MultipartFile file,
																												    		@PathVariable Integer productId ) throws IOException {
																												        return pimagesService.savePimages(file, productId );
																												    }
																												    
	// 上傳新圖片 ，有滑鼠移入移出事件測試，切換不同的圖片
//	@PostMapping("/admin/products/images/{productId}/create")
//	public PimagesDTO uploadImage(@RequestParam("file") MultipartFile file, @PathVariable Integer productId,
//			@RequestParam(required = false) String imageType) throws IOException {
//		return pimagesService.savePimages(file, productId, imageType);
//	}

																													// 上傳多張圖片
																												    @PostMapping("/admin/products/images/{productId}/multiple")
																												    public List<PimagesDTO> uploadMultipleImages(@RequestParam("file") MultipartFile[] files,
																												    		@PathVariable Integer productId ) throws IOException {
																												        return pimagesService.saveMultiplePimages(files, productId );
																												    }

	// 上傳多張圖片，有滑鼠移入移出事件測試，切換不同的圖片
//	@PostMapping("/admin/products/images/{productId}/multiple")
//	public List<PimagesDTO> uploadMultipleImages(@RequestParam("file") MultipartFile[] file,
//			@PathVariable Integer productId, @RequestParam(required = false ,defaultValue = "default") String imageType) throws IOException {
//		return pimagesService.saveMultiplePimages(file, productId, imageType);
//	}

	// 更新圖片
	@PutMapping("/admin/products/images/{productId}/{id}")
	public PimagesDTO updateImage(@RequestParam("file") MultipartFile file, @PathVariable Integer productId,
			@PathVariable("id") Integer id) throws IOException {
		return pimagesService.updatePimages(file, id);
	}

	// 刪除圖片
	@DeleteMapping("/admin/products/images/{productId}/{id}")
	public PimagesDTO deleteImage(@PathVariable Integer productId, @PathVariable("id") Integer id) {
		return pimagesService.deletePimages(id);
	}

	// 獲取單個圖片信息
	@GetMapping("/products/{productId}/images/{id}")
	public PimagesDTO getImage(@PathVariable Integer productId, @PathVariable("id") Integer id) {
		return pimagesService.findPimagesById(id);
	}

	// 獲取商品的所有圖片
	@GetMapping("/{productId}/images")
	public List<PimagesDTO> getAllProductImages(@PathVariable Integer productId) {
		return pimagesService.findAllImagesByProductId(productId);
	}

	// 封面照片
	@GetMapping("/{productId}/cover")
	public PimagesDTO getCoverPhoto(@PathVariable Integer productId) {
		return pimagesService.findCoverPhoto(productId);
	}
	
	
	//移入時換照片
	@GetMapping("/{productId}/images/hover")
	public PimagesDTO getHoverImage(@PathVariable Integer productId, @RequestParam String imageName) {
	    return pimagesService.findImageByProductIdAndImageName(productId, imageName);
	}
}