package com.outfit_share.entity.post;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.outfit_share.entity.users.UserDetail;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer postId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserDetail userDetail;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comment;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post")
    private List<Images> images;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post")
    private List<Likes> likes;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post")
    private List<Collections> collections;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post")
    private List<PostTags> postTags;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "post_title", unique = true, nullable = false) // 標題不能重複?
    private String postTitle;

    @Column(name = "content_text")
    private String contentText;

    @Column(name = "share_id")
    private Integer shareId;

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

    public Post() {
    }

    public Post(Integer postId, String contentType, String postTitle, String contentText, Date createdAt,
            Date deletedAt) {
        this.postId = postId;
        this.contentType = contentType;
        this.postTitle = postTitle;
        this.contentText = contentText;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public Post(Integer postId, UserDetail userDetail, String contentType, String postTitle, String contentText,
    Integer shareId,
            Date createdAt, Date deletedAt) {
        this.postId = postId;
        this.userDetail = userDetail;
        this.contentType = contentType;
        this.postTitle = postTitle;
        this.contentText = contentText;
        this.shareId = shareId;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    // public Integer getPostId() {
    //     return postId;
    // }

    // public void setPostId(Integer postId) {
    //     this.postId = postId;
    // }

    // public UserDetail getUserDetail() {
    //     return userDetail;
    // }

    // public void setUserDetail(UserDetail userDetail) {
    //     this.userDetail = userDetail;
    // }

    // public String getContentType() {
    //     return contentType;
    // }

    // public void setContentType(String contentType) {
    //     this.contentType = contentType;
    // }

    // public String getPostTitle() {
    //     return postTitle;
    // }

    // public void setPostTitle(String postTitle) {
    //     this.postTitle = postTitle;
    // }

    // public String getContentText() {
    //     return contentText;
    // }

    // public void setContentText(String contentText) {
    //     this.contentText = contentText;
    // }

    // public Integer getShareId() {
    //     return shareId;
    // }

    // public void setShareId(Integer shareId) {
    //     this.shareId = shareId;
    // }

    // public Date getCreatedAt() {
    //     return createdAt;
    // }

    // public void setCreatedAt(Date createdAt) {
    //     this.createdAt = createdAt;
    // }

    // public Date getDeletedAt() {
    //     return deletedAt;
    // }

    // public void setDeletedAt(Date deletedAt) {
    //     this.deletedAt = deletedAt;
    // }

}
