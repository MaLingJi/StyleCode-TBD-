package com.outfit_share.entity.post;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class LikesId implements Serializable {

    private Integer userId;

    private Integer postId;

    public LikesId() {
    }

    public LikesId(Integer userId, Integer postId) {
        this.userId = userId;
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((postId == null) ? 0 : postId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LikesId other = (LikesId) obj;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (postId == null) {
            if (other.userId != null)
                return false;
        } else if (!postId.equals(other.postId))
            return false;
        return true;
    }
}
