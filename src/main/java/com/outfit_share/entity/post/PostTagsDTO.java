package com.outfit_share.entity.post;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostTagsDTO {

    private Integer tagId;
    private Integer postId;

    public PostTagsDTO(PostTags postTags) {
        this.tagId = postTags.getPostTagsId().getTagsId();
        this.postId = postTags.getPostTagsId().getPostId();
    }
}
