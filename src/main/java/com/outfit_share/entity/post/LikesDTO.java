package com.outfit_share.entity.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LikesDTO {
    private Integer userId;
    private Integer postId;

    public LikesDTO(Likes likes) {
        this.userId = likes.getLikesId().getUserId();
        this.postId = likes.getLikesId().getPostId();
    }
}
