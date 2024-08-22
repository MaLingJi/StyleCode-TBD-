package com.outfit_share.service.post;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.outfit_share.entity.post.Images;
import com.outfit_share.entity.post.Post;
import com.outfit_share.repository.post.ImagesRepository;
import com.outfit_share.repository.post.PostRepository;

@Service
public class ImagesService {

    @Value("${post.upload.dir}")
    private String uploadDir;
    
    @Autowired
    private ImagesRepository imagesRepo;
    
    @Autowired
    private PostRepository postRepo;
    
    public List<Images> createImages(List<MultipartFile> files, Integer postId) throws IOException {
        Post post = postRepo.findById(postId)
            .orElseThrow(() -> new RuntimeException("找不到 id 的 post: " + postId));
        
        List<Images> savedImages = new ArrayList<>();
        
        for (MultipartFile file : files) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
            
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                try {
                    Files.createDirectories(uploadPath);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("創建上傳目錄時出錯", e);
                }
            }
            Path filePath = uploadPath.resolve(uniqueFileName);
            try {
                file.transferTo(filePath.toFile());
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("文件傳輸過程中出錯", e);
            }
            Images images = new Images();
            images.setImgurl(filePath.toString());
            images.setPostId(post);
            
            savedImages.add(imagesRepo.save(images));
        }
        return savedImages;
    }
    
    public Images findImagesById(Integer id) {
    	Optional<Images> optional = imagesRepo.findById(id);
    	
    	if (optional.isPresent()) {
			return optional.get();
		}
    	return null;
    }
    
    public List<Images> findAllImages() {
        return imagesRepo.findAll();
    }
    
    @Transactional
    public Images updateImage(Integer id, MultipartFile file) throws IOException{
        Optional<Images> optionalImage = imagesRepo.findById(id);
        
        if (optionalImage.isPresent()) {
            Images image = optionalImage.get();
            //生成新的文件名和路徑
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
            Path filePath = Paths.get(uploadDir).resolve(uniqueFileName);
            
            // 確保上傳目錄存在
            if (!Files.exists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
            }
            // 保存新文件
            file.transferTo(filePath.toFile());

            // 更新圖片 URL
            image.setImgurl(filePath.toString());
            return imagesRepo.save(image);
        }
        return null;
    }
    
    public void deleteImagesById(Integer id) {
    	Optional<Images> optionalImage = imagesRepo.findById(id);
    	
    	if (optionalImage.isPresent()) {
    		imagesRepo.deleteById(id);
    	} else {
    		throw new RuntimeException("找不到id的image:" + id);
    	}
    }
    //按postId查找多張照片
    public List<Images> findImagesByPostId(Integer postId) {
        return imagesRepo.findByPost_PostId(postId);
    }
}
