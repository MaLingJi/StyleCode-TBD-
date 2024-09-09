package com.outfit_share.controller.posts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outfit_share.entity.post.Collections;
import com.outfit_share.entity.post.CollectionsDTO;
import com.outfit_share.entity.post.CollectionsId;
import com.outfit_share.entity.post.Post;
import com.outfit_share.entity.post.PostDTO;
import com.outfit_share.entity.users.UserDetail;
import com.outfit_share.service.post.CollectionsService;

@RestController
@RequestMapping("/collections")
public class CollectionsController {

	@Autowired
	private CollectionsService collectservice;

	@PostMapping
	public ResponseEntity<String> addCollections(@RequestBody Collections collections) {
		if (collections == null) {
			return ResponseEntity.badRequest().body("傳入的收藏物件為空");
		}
		Post post = collections.getPosts();
		UserDetail userDetail = collections.getUserDetail();
		if (post == null) {
			return ResponseEntity.badRequest().body("貼文為空");
		}
		if (userDetail == null) {
			return ResponseEntity.badRequest().body("用戶詳細信息為空");
		}
		// 設置CollectionsId 並檢查是否已存在
		CollectionsId collectionsId = new CollectionsId(userDetail.getId(), post.getPostId());
		collections.setCollectionsId(collectionsId);
		// 檢查是否已經存在該收藏
		if (collectservice.findCollectionsbyId(userDetail.getId(), post.getPostId()) != null) {
			return ResponseEntity.badRequest().body("已經在收藏區");
		}

		collectservice.createcollections(collections);
		return ResponseEntity.ok("收藏成功");
	}

	@GetMapping("/{userId}/{postId}")
	public ResponseEntity<String> findCollectionsById(@PathVariable Integer userId, @PathVariable Integer postId) {
		Collections collections = collectservice.findCollectionsbyId(userId, postId);
		if (collections == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok("貼文已收藏");// <collections>
	}

	@DeleteMapping("/{userId}/{postId}")
	public ResponseEntity<String> deleteCollections(@PathVariable Integer userId, @PathVariable Integer postId) {
		Collections collections = collectservice.findCollectionsbyId(userId, postId);
		if (collections == null) {
			return ResponseEntity.notFound().build();
		}
		collectservice.deletecollectsById(postId, userId);
		return ResponseEntity.ok("取消收藏");
	}

	@PostMapping("/toggle")
	public ResponseEntity<String> toggleLike(@RequestBody CollectionsDTO collectionsDTO) {
		boolean isCollected = collectservice.toggleCollects(collectionsDTO.getUserId(), collectionsDTO.getPostId());
		if (isCollected) {
			return ResponseEntity.ok("收藏成功");
		} else {
			return ResponseEntity.ok("收回收藏成功");
		}
	}

	// 該使用者的收藏文章
	@GetMapping("/post/{userId}")
	public List<PostDTO> findCollectionsPostsByUserId(@PathVariable("userId") Integer userId) {
		return collectservice.findCollectionsPostsByUserId(userId);
	}
}
