package com.outfit_share.controller.posts;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.outfit_share.entity.post.Images;
import com.outfit_share.service.post.ImagesService;

@RestController
@RequestMapping("/images")
public class ImagesController {

	@Autowired
	private ImagesService imagesService;

	@PostMapping
    public List<Images> addImages(@RequestParam("file") List<MultipartFile> file, @RequestParam("postId") Integer postId) {
        try {
        	return imagesService.createImages(file, postId);
        }catch (IOException e) {
        	throw new RuntimeException("圖片上船過程中出錯",e);
        }
    }

	@GetMapping("/{id}")
	public Images findImagesById(@PathVariable Integer id) {
		return imagesService.findImagesById(id);
	}

	@GetMapping
	public List<Images> findAllImages() {
		return imagesService.findAllImages();
	}
	
	@PutMapping("/{id}")
	public Images updateImages(@PathVariable Integer id,@RequestParam("file") MultipartFile file) throws IOException {
		return imagesService.updateImage(id,file);
	}
	
	@DeleteMapping("/{id}")  // TODO: 需要加上deleteAt以實現軟刪除圖片
	public void deleteImages(@PathVariable Integer id) {
		imagesService.deleteImagesById(id);
	}
	//按postId查找多張照片
	@GetMapping("/post/{postId}")
    public List<Images> findImagesByPostId(@PathVariable Integer postId) {
        return imagesService.findImagesByPostId(postId);
    }
}