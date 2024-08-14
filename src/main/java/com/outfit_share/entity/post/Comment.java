package com.outfit_share.entity.post;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.outfit_share.entity.users.UserDetail;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comments_id")
	private Integer commentsId;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post posts;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserDetail userDetail;

	@Column(name = "comment")
	private String comment;

	@DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss EEEE")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	public Comment() {
	}

	public Comment(Post posts, UserDetail userDetail, String comment, Date createdAt) {
		this.posts = posts;
		this.userDetail = userDetail;
		this.comment = comment;
		this.createdAt = createdAt;
	}

	public Comment(Integer commentsId, Post posts, UserDetail userDetail, String comment, Date createdAt) {
		this.commentsId = commentsId;
		this.posts = posts;
		this.userDetail = userDetail;
		this.comment = comment;
		this.createdAt = createdAt;
	}

	public Integer getCommentsId() {
		return commentsId;
	}

	public void setCommentsId(Integer commentsId) {
		this.commentsId = commentsId;
	}

	public Post getPosts() {
		return posts;
	}

	public void setPosts(Post posts) {
		this.posts = posts;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
