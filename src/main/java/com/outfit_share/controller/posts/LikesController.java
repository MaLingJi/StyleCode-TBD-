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
import com.outfit_share.service.post.LikesService;

@RestController
@RequestMapping("/likes")
public class LikesController {

	@Autowired
	private LikesService likeservice;

	@PostMapping("/{postId}/{userId}")
	public ResponseEntity<?> addLikes(@PathVariable Integer postId, @PathVariable Integer userId) {
		if (likeservice.existsByPostIdAndUserId(postId, userId)) {
			return ResponseEntity.badRequest().body("Like already exists");
		}
		Likes like = new Likes(postId, userId);
		likeservice.save(like);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/{postId}/{userId}")
	public ResponseEntity<String> findLikesById(@PathVariable Integer postId, @PathVariable Integer userId) {
		Likes likes = likeservice.findLikesById(postId, userId);
		if (likes == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok("對方對你點讚");// <likes>
	}

	@DeleteMapping("/{postId}/{userId}")
	public ResponseEntity<?> deleteLikes(@PathVariable Integer postId, @PathVariable Integer userId) {
		Likes likes = likeservice.findLikesById(postId, userId);
		if (likes == null) {
			return ResponseEntity.notFound().build();
		}
		likeservice.deleteLikesById(postId, userId);
		return ResponseEntity.ok().build();
	}
	 @PostMapping("/{postId}/{userId}")
	    public ResponseEntity<?> toggleLike(@PathVariable Integer postId, @PathVariable Integer userId) {
		 likeservice.toggleLike(postId, userId);
	        return ResponseEntity.ok().build();
	 }
}
