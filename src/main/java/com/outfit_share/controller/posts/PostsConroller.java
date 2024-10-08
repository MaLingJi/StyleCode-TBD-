package com.outfit_share.controller.posts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.outfit_share.entity.post.Post;
import com.outfit_share.entity.post.PostCreationRequest;
import com.outfit_share.entity.post.PostDTO;
import com.outfit_share.service.post.PostService;

@RestController
// @RequestMapping("/post")
public class PostsConroller {

	@Autowired
	private PostService postService;

	@PostMapping("/post")
	public Post addPost(@RequestBody Post post) {
		return postService.createPost(post);
	}

	@PostMapping("/post/postwithtags")
	public ResponseEntity<PostDTO> createPost(@RequestBody PostCreationRequest request) {
		PostDTO createdPost = postService.createPostWithTags(request.getPostDTO());
		return ResponseEntity.ok(createdPost);
	}

	@GetMapping("/post/{id}")
	public PostDTO findPostById(@PathVariable("id") Integer postId) {
		return postService.findPostById(postId);
	}

	@GetMapping("/post")
	public List<PostDTO> findAllPosts() {
		return postService.findAllPost();
	}

	@PutMapping("/post/postwithtags/{postId}")
	public ResponseEntity<PostDTO> updatePost(
			@PathVariable Integer postId,
			@RequestBody PostCreationRequest request) {

		System.out.println("Received PostCreationRequest: " + request);
		PostDTO updatedPost = postService.updatePostWithTags(postId, request.getPostDTO());

		return ResponseEntity.ok(updatedPost);
	}

	@PutMapping("/post/{id}")
	public Post updatePost(@PathVariable("id") Integer postId, @RequestBody Post post) {
		return postService.updatePost(postId, post);
	}

	@DeleteMapping("/post/{id}")
	public void deletePost(@PathVariable("id") Integer postId) {
		postService.deletePostById(postId);
	}

	// 模糊搜尋文章中分類 分享/討論 的標題
	@GetMapping("/post/type")
	public List<Post> searchPosts(
			@RequestParam(value = "contentType", required = false) String contentType,
			@RequestParam(value = "keyword", required = false) String keyword) {
		return postService.searchPostsByTypeAndKeyword(contentType, keyword);
	}
	// TODO: 要改成只有一個參數時也可搜尋，無參數時就findAll
	// P.S.這邊我改成靠前端綁定來處理即可

	// 用戶 ID 查詢該用戶的所有文章
	@GetMapping("/post/user/{userId}")
	public List<PostDTO> findPostsByUserId(@PathVariable("userId") Integer userId) {
		return postService.findPostsByUserId(userId);
	}

	// 只取分享區的 資料
	@GetMapping("/post/latest-share")
	public List<PostDTO> getLatestSharePosts(@RequestParam(defaultValue = "17") int limit) {
		return postService.findLatestSharePosts(limit);
	}

	//只取前9筆資料
	@GetMapping("/post/most-liked")
	public List<PostDTO> getMostLikedPosts(@RequestParam(defaultValue = "9") Integer limit) {
		return postService.findMostLikedPosts(limit);
	}

}
