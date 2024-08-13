package com.outfit_share.entity.post;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comments_id")
	private int commentsId;
	
	@JoinColumn(name = "post_id")
	private int postId;
	
	@JoinColumn(name = "user_id")
	private int userId;
	
	@Column(name = "comment")
	private String comMent;
	
	@DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss EEEE")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;
	
	public Comment() {
	}

	public Comment(int commentsId, int postId, int userId, String comMent, Date createdAt) {
		this.commentsId = commentsId;
		this.postId = postId;
		this.userId = userId;
		this.comMent = comMent;
		this.createdAt = createdAt;
	}

	public Comment(int commentsId, String comMent, Date createdAt) {
		this.commentsId = commentsId;
		this.comMent = comMent;
		this.createdAt = createdAt;
	}

	public int getCommentsId() {
		return commentsId;
	}

	public void setCommentsId(int commentsId) {
		this.commentsId = commentsId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getComMent() {
		return comMent;
	}

	public void setComMent(String comMent) {
		this.comMent = comMent;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
