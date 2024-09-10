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

import com.outfit_share.entity.post.Comment;
import com.outfit_share.entity.post.CommentDTO;
import com.outfit_share.service.post.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService comtService;

	@PostMapping
	public Comment addComment(@RequestBody Comment comment) {
		return comtService.createComment(comment);
	}

	@GetMapping("/{id}")
	public Comment findCommentById(@PathVariable("id") Integer commentId) {
		return comtService.findCommentById(commentId);
	}

	@GetMapping
	public List<CommentDTO> findAllComments(@RequestParam Integer postId) {
		return comtService.findAllComments(postId);
	}

	@PutMapping("/{id}")
	public Comment updateComment(@PathVariable("id") Integer commentId, @RequestBody Comment comment) {
		return comtService.upComment(commentId, comment);
	}

	@DeleteMapping("/{id}")
	public void deleteComment(@PathVariable("id") Integer commentId) {
		comtService.deleteCommentById(commentId);
	}
}
