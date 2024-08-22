package com.outfit_share.repository.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.post.Images;

public interface ImagesRepository extends JpaRepository<Images, Integer>{

	//按postId查找多張照片
	List<Images> findByPost_PostId(Integer postId);
}