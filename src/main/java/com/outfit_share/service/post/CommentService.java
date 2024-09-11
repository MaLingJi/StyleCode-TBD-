package com.outfit_share.service.post;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.outfit_share.entity.post.Comment;
import com.outfit_share.entity.post.CommentDTO;
import com.outfit_share.entity.post.Post;
import com.outfit_share.entity.users.Notifications;
import com.outfit_share.entity.users.UserDetail;
import com.outfit_share.repository.post.CommentRepository;
import com.outfit_share.repository.post.PostRepository;
import com.outfit_share.repository.users.NotificationsRepository;
import com.outfit_share.repository.users.UserDetailRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepo;

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private UserDetailRepository userDetailRepo;

	@Autowired
	private NotificationsRepository notiRepo;

	// 依賴userDetailRepo和postRepo找尋文章中的postid,沒寫postid回傳為null
	public Comment createComment(Comment comment) {
		Post post = postRepo.findById(comment.getPost().getPostId())
				.orElseThrow(() -> new IllegalArgumentException("找不到貼文"));
		UserDetail userDetail = userDetailRepo.findById(comment.getUserDetail().getId())
				.orElseThrow(() -> new IllegalArgumentException("未找到用戶詳細信息"));

		comment.setPost(post);
		comment.setUserDetail(userDetail);
		comment.setCreatedAt(new Date());

		// 創建新通知
		UserDetail postOwner = post.getUserDetail();
		Notifications notification = new Notifications();
		notification.setMessage(userDetail.getUserName() + "回應了您的文章 : " + comment.getComment());
		notification.setCreatedTime(new Date());
		notification.setStatus(0);
		notification.setType("post");
		notification.setUserDetail(postOwner);
		notiRepo.save(notification);
		return commentRepo.save(comment);
	}

	public Comment findCommentById(Integer commentId) {
		Optional<Comment> optional = commentRepo.findById(commentId);

		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	public List<Comment> findAllComment() {
		return commentRepo.findAll();
	}

	// 軟刪除 設置當前時間
	public Comment deleteCommentById(Integer commentId) {
		Optional<Comment> deoptional = commentRepo.findById(commentId);

		if (deoptional.isPresent()) {
			Comment comment = deoptional.get();
			comment.setDeletedAt(new Date());
			return commentRepo.save(comment);
		}
		return null;
	}

	@Transactional
	public Comment upComment(Integer commentId, Comment newComment) {
		Optional<Comment> upoptional = commentRepo.findById(commentId);

		if (upoptional.isPresent()) {
			Comment comment = upoptional.get();
			comment.setComment(newComment.getComment());
			return commentRepo.save(comment);
		}
		return null;
	}

	// 獲取評論時 將Comment實體轉換為 CommentDTO
	public List<CommentDTO> findAllComments(Integer postId) {
		List<Comment> comments = commentRepo.findByPost_PostId(postId);
	    return comments.stream().map(comment -> {
	            CommentDTO dto = new CommentDTO(comment);
	            dto.setUserName(comment.getUserDetail().getUserName());
	            dto.setUserPhoto(comment.getUserDetail().getUserPhoto());
	            return dto;
	        })
	        .collect(Collectors.toList());
	}
}
