package com.outfit_share.controller.posts;

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

import com.outfit_share.entity.post.Post;
import com.outfit_share.service.post.PostService;

@RestController
@RequestMapping("/post")
public class PostsConroller {

	@Autowired
	private PostService postService;

	@PostMapping
	public Post addPost(@RequestBody Post post) {
		return postService.createPost(post);
	}

	@GetMapping("/{id}")
	public Post findPostById(@PathVariable("id") Integer postId) {
		return postService.findPostById(postId);
	}

	@GetMapping
	public List<Post> findAllPosts() {
		return postService.findAllPost();
	}

	@PutMapping("/{id}")
	public Post updatePost(@PathVariable("id") Integer postId, @RequestBody Post post) {
		return postService.updatePost(postId, post);
	}

	@DeleteMapping("/{id}")
	public void deletePost(@PathVariable("id") Integer postId) {
		postService.deletePostById(postId);
	}

	//模糊搜尋文章中分類 分享/討論 的標題
	@GetMapping("/type")
	public List<Post> searchPosts(@RequestParam String contentType,@RequestParam String keyword){
		return postService.searchPostsByTypeAndKeyword(contentType, keyword);
	}
}
