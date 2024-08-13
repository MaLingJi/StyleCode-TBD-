package com.outfit_share.entity.post;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "posts")
public class Posts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private int postId;

	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false)
	private Users users;

	@Column(name = "content_type")
	private String contentType;

	@Column(name = "post_title",unique = true, nullable = false) // 標題不能重複?
	private String postTitle;

	@Column(name = "content_text")
	private String contentText;

	@Column(name = "share_id")
	private int shareId;

	@DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss EEEE")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss EEEE")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted_at",nullable = true)
	private Date deletedAt;
	
	//創建時會自動創時間
	@PrePersist 
	public void onCreate() {
		if(createdAt ==null) {
			createdAt= new Date();
		}
	}
	
	public Posts() {
	}

	public Posts(int postId, String contentType, String postTitle, String contentText, Date createdAt,
			Date deletedAt) {
		this.postId = postId;
		this.contentType = contentType;
		this.postTitle = postTitle;
		this.contentText = contentText;
		this.createdAt = createdAt;
		this.deletedAt = deletedAt;
	}

	public Posts(int postId, Users users, String contentType, String postTitle, String contentText, int shareId,
			Date createdAt, Date deletedAt) {
		this.postId = postId;
		this.users = users;
		this.contentType = contentType;
		this.postTitle = postTitle;
		this.contentText = contentText;
		this.shareId = shareId;
		this.createdAt = createdAt;
		this.deletedAt = deletedAt;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public int getShareId() {
		return shareId;
	}

	public void setShareId(int shareId) {
		this.shareId = shareId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
}
