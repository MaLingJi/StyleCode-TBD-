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
@RequestMapping("/api/products/{productId}/images")
public class PimagesController {

    @Autowired
    private PimagesService pimagesService;

    // 上傳新圖片
    @PostMapping
    public PimagesDTO uploadImage(@RequestParam("file") MultipartFile file, @PathVariable Integer productId) throws IOException {
        return pimagesService.savePimages(file, productId);
    }
    
    //上傳多張圖片
    @PostMapping("/multiple")
    public List<PimagesDTO> uploadMultipleImages(@RequestParam("file") MultipartFile[] files, @PathVariable Integer productId) throws IOException {
        return pimagesService.saveMultiplePimages(files, productId);
    }

    // 更新圖片
    @PutMapping("/{imageId}")
    public PimagesDTO updateImage(@RequestParam("file") MultipartFile file, 
                               @PathVariable Integer productId,
                               @PathVariable Integer imageId) throws IOException {
        return pimagesService.updatePimages(file, imageId);
    }
    
    // 刪除圖片
    @DeleteMapping("/{imageId}")
    public PimagesDTO deleteImage(@PathVariable Integer productId, @PathVariable Integer imageId) {
    	return pimagesService.deletePimages(imageId);
    }

    // 獲取單個圖片信息
    @GetMapping("/{imageId}")
    public PimagesDTO getImage(@PathVariable Integer productId, @PathVariable Integer imageId) {
        return pimagesService.findPimagesById(imageId);
    }

    // 獲取商品的所有圖片
    @GetMapping
    public List<PimagesDTO> getAllProductImages(@PathVariable Integer productId) {
        return pimagesService.findAllImagesByProductId(productId);
    }
    
    // 封面照片
    @GetMapping("/cover")
    public PimagesDTO getCoverPhoto(@PathVariable Integer productId) {
    	return pimagesService.findCoverPhoto(productId);
    }
}