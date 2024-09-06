package com.outfit_share.entity.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PostTagsDTO {

    private Integer tagId;
    private Integer postId;
    private String tagName;

    public PostTagsDTO(String tagName) {
        this.tagName = tagName;
    }

    public PostTagsDTO(PostTags postTags) {
        this.tagId = postTags.getPostTagsId().getTagsId();
        this.postId = postTags.getPostTagsId().getPostId();
        this.tagName = postTags.getTags().getName();
    }
}
