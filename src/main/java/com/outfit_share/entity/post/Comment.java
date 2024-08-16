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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Integer commentId;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserDetail userDetail;

	@Column(name = "comment")
	private String comment;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at", nullable = true)
    private Date deletedAt;

    // 創建時會自動創時間
    @PrePersist
    public void onCreate() {
        if (createdAt == null) {
            createdAt = new Date();
        }
    }
	
	public Comment() {
	}

	public Comment(Post post, UserDetail userDetail, String comment, Date createdAt, Date deletedAt) {
		this.post = post;
		this.userDetail = userDetail;
		this.comment = comment;
		this.createdAt = createdAt;
		this.deletedAt = deletedAt;
	}

	public Comment(Integer commentId, Post post, UserDetail userDetail, String comment, Date createdAt, Date deletedAt) {
		this.commentId = commentId;
		this.post = post;
		this.userDetail = userDetail;
		this.comment = comment;
		this.createdAt = createdAt;
		this.deletedAt = deletedAt;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Post getPosts() {
		return post;
	}

	public void setPosts(Post post) {
		this.post = post;
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

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

}
