package com.outfit_share.entity.post;

import java.util.Date;

public class CommentDTO {

    private Integer commentId;
    private Integer postId;
    private Integer userId;
    private String userName;
    private String commentText;
    private Date createdAt;
    private Date deletedAt;

    public CommentDTO() {
    }

    public CommentDTO(Comment comment) {
        this.commentId = comment.getCommentId();
        this.postId = comment.getPost().getPostId();  // 確保 Post 實體已初始化
        this.userId = comment.getUserDetail().getId();  // 確保 UserDetail 實體已初始化
        this.userName = comment.getUserDetail().getUserName();  // 確保 UserDetail 實體已初始化
        this.commentText = comment.getComment();
        this.createdAt = comment.getCreatedAt();
        this.deletedAt = comment.getDeletedAt();
    }

    // Getters and setters
    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
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
