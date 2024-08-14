package com.outfit_share.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.outfit_share.entity.product.Pimages;
import com.outfit_share.service.product.PimagesSerivce;

import java.io.IOException;
import java.util.List;

@RestController
//@RequestMapping("/api/products/{productId}/images")
public class PimagesController {

    @Autowired
    private PimagesSerivce pimagesService;

    // 上傳新圖片
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pimages uploadImage(@RequestParam("file") MultipartFile file, @PathVariable Integer productId) throws IOException {
        return pimagesService.savePimages(file, productId);
    }

    // 更新圖片
    @PutMapping("/{imageId}")
    public Pimages updateImage(@PathVariable Integer productId, @PathVariable Integer imageId, @RequestBody Pimages img) {
        return pimagesService.updatePimages(imageId, img);
    }

    // 刪除圖片
    @DeleteMapping("/{imageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable Integer productId, @PathVariable Integer imageId) {
        pimagesService.deletePimages(imageId);
    }

    // 獲取單個圖片信息
    @GetMapping("/{imageId}")
    public Pimages getImage(@PathVariable Integer productId, @PathVariable Integer imageId) {
        return pimagesService.findPimagesById(imageId);
    }

    // 獲取商品的所有圖片
    @GetMapping
    public List<Pimages> getAllProductImages(@PathVariable Integer productId) {
        return pimagesService.findAllImagesByProductId(productId);
    }
}