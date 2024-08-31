package com.outfit_share.entity.post;

import java.util.Date;

import java.util.List;

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
    private Integer shareId;
    private Date deletedAt;
    private Date createdAt;

    private Integer userId;
    private String userName;
    private String userPhoto;

    private List<CollectionsDTO> collections;
    private List<CommentDTO> comments;
    private List<ImagesDTO> images;
    private List<LikesDTO> likes;
    private List<PostTagsDTO> postTags;

    public PostDTO(Post post) {
        this.postId = post.getPostId();
        this.contentType = post.getContentType();
        this.postTitle = post.getPostTitle();
        this.contentText = post.getContentText();
        this.shareId = post.getShareId();
        this.deletedAt = post.getDeletedAt();
        this.createdAt = post.getCreatedAt();

        if (post.getUserDetail() != null) {
            this.userId = post.getUserDetail().getId();
            this.userName = post.getUserDetail().getUserName();
            this.userPhoto = post.getUserDetail().getUserPhoto();
        }

        for (Comment comment : post.getComment()) {
            this.comments.add(new CommentDTO(comment));
        }

        for (Images image : post.getImages()) {
            this.images.add(new ImagesDTO(image));
        }

        for (Likes like : post.getLikes()) {
            this.likes.add(new LikesDTO(like));
        }

        for (Collections collection : post.getCollections()) {
            this.collections.add(new CollectionsDTO(collection));
        }

        for (PostTags postTag : post.getPostTags()) {
            this.postTags.add(new PostTagsDTO(postTag));
        }
    }
}
