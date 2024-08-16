package com.outfit_share.entity.post;

import org.springframework.stereotype.Component;

import com.outfit_share.entity.users.UserDetail;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "collections")
@Component
public class Collections {

    @EmbeddedId
    private CollectionsId collectionsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name="user_id")
    private UserDetail userDetail;

    public Collections() {
    }

    public CollectionsId getCollectionsId() {
        return collectionsId;
    }

    public void setCollectionsId(CollectionsId collectionsId) {
        this.collectionsId = collectionsId;
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

}
