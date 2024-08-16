package com.outfit_share.service.post;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.outfit_share.entity.post.Comment;
import com.outfit_share.repository.post.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepo;
	
	public Comment createComment(Comment comment) {
		comment.setCreatedAt(new Date());
		return commentRepo.save(comment);
	}
	
	public Comment findCommentsById(Integer commentId) {
		Optional<Comment> optional = commentRepo.findById(commentId);
		
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	public List<Comment> findAllComment(){
		return commentRepo.findAll();
	}
	
	//軟刪除 設置當前時間
	public Comment deleteCommentById(Integer commentId) {
		Optional<Comment> deoptional = commentRepo.findById(commentId);
		
		if(deoptional.isPresent()) {
			Comment comment = deoptional.get();
			comment.setDeletedAt(new Date());
			return commentRepo.save(comment);
		}
		return null;
	}
	
	@Transactional
	public Comment upComment(Integer commentId,String newComment) {
		Optional<Comment> upoptional = commentRepo.findById(commentId);
		
		if(upoptional.isPresent()) {
			Comment comment = upoptional.get();
			comment.setComment(newComment);
			return comment;
		}
		return null;
	}
}
