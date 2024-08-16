package com.outfit_share.controller.posts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.outfit_share.entity.post.Comment;
import com.outfit_share.service.post.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService comtService;
	
	@PostMapping
	public Comment addComment(@RequestParam Comment comment) {
		return comtService.createComment(comment);
	}
	
	@GetMapping("/{id}")
	public Comment findCommentById(@PathVariable Integer commentId) {
		return comtService.findCommentById(commentId);
	}
	
	@GetMapping
	public List<Comment> findAllComments(){
		return comtService.findAllComments();
	}
	
	@PutMapping("/{id}")
	public Comment updateComment(@PathVariable Integer commentId,@RequestBody Comment comment) {
		return comtService.upComment(commentId, comment);
	}
	
	@DeleteMapping("/{id}")
	public void deleteComment(@PathVariable Integer commentId) {
		comtService.deleteCommentById(commentId);
	}
	
}
