package com.outfit_share.entity.post;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "Collections")
public class Collections {

	@Id
	@JoinColumn(name = "user_id")
	private int userId;
	
	@JoinColumn(name = "post_id")
	private int postId;
	
	public Collections() {
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}
}
