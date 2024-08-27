package com.outfit_share.entity.post;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class PostDTO {

    private Integer postId;
    private String contentType;
    private String postTitle;
    private String contentText;
    private int shareId;
    private Date deletedAt;
    private Date createdAt;

    private Integer userId;
    private String userName;
    // private String userPhoto;

    public PostDTO(Post post) {
        this.postId = post.getPostId();
        this.contentType = post.getContentType();
        this.postTitle = post.getPostTitle();
        this.contentText = post.getContentText();
        this.shareId = post.getShareId();
        this.deletedAt = post.getDeletedAt();
        this.createdAt = post.getCreatedAt();

        this.userId = post.getUserDetail().getId();
        this.userName = post.getUserDetail().getUserName();
        // this.userPhoto = post.getUserDetail().getUserPhoto();
    }


    
    // public PostDTO(Post post) {
    //     this.postId = post.getPostId();
    //     if (post.getUserDetail() != null) {
    //         this.userId = post.getUserDetail().getId();
    //         this.userName = post.getUserDetail().getUserName();
    //     }
    // }
}
