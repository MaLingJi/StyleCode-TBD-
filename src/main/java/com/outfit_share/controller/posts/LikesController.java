package com.outfit_share.controller.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outfit_share.entity.post.Likes;
import com.outfit_share.entity.post.LikesDTO;
import com.outfit_share.entity.post.LikesId;
import com.outfit_share.entity.post.Post;
import com.outfit_share.entity.users.UserDetail;
import com.outfit_share.service.post.LikesService;

@RestController
@RequestMapping("/likes")
public class LikesController {

	@Autowired
	private LikesService likeservice;

	@PostMapping
	public ResponseEntity<String> addLikes(@RequestBody Likes likes) {
		Post post = likes.getPost();
		UserDetail userDetail = likes.getUserDetail();
		// 手動創建 LikesId 並設置
		LikesId likesId = new LikesId(userDetail.getId(), post.getPostId());
		likes.setLikesId(likesId);
		// 檢查是否已經點讚過
		Likes nowlikes = likeservice.findLikesById(userDetail.getId(), post.getPostId());
		if (nowlikes != null) {
			return ResponseEntity.badRequest().body("已點過讚");
		}
		likeservice.createLikes(likes);
		return ResponseEntity.ok("對你按讚");
	}

	@GetMapping("/{userId}/{postId}")
	public ResponseEntity<String> findLikesById(@PathVariable Integer userId, @PathVariable Integer postId) {
		Likes likes = likeservice.findLikesById(userId, postId);
		if (likes == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok("對方對你點讚");// <likes>
	}

	@DeleteMapping("/{userId}/{postId}")
	public ResponseEntity<String> deleteLikes(@PathVariable Integer userId, @PathVariable Integer postId) {
		Likes likes = likeservice.findLikesById(userId, postId);
		if (likes == null) {
			return ResponseEntity.notFound().build();
		}
		likeservice.deleteLikesById(userId, postId);
		return ResponseEntity.ok("收回讚");
	}

	@PostMapping("/toggle")
	public ResponseEntity<String> toggleLike(@RequestBody LikesDTO likesDTO) {
		boolean isLiked = likeservice.toggleLikes(likesDTO.getUserId(), likesDTO.getPostId());
		if (isLiked) {
			return ResponseEntity.ok("按讚成功");
		} else {
			return ResponseEntity.ok("按讚收回成功");
		}
	}
}
