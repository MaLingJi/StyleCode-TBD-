package com.outfit_share.entity.post;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor

@Embeddable
public class PostTagsId implements Serializable {

    private Integer tagsId;

    private Integer postId;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tagsId == null) ? 0 : tagsId.hashCode());
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
        PostTagsId other = (PostTagsId) obj;
        if (tagsId == null) {
            if (other.tagsId != null)
                return false;
        } else if (!tagsId.equals(other.tagsId))
            return false;
        if (postId == null) {
            if (other.postId != null)
                return false;
        } else if (!postId.equals(other.postId))
            return false;
        return true;
    }
}