package com.outfit_share.entity.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CollectionsDTO {
    private Integer userId;
    private Integer postId;

    public CollectionsDTO(Collections collections) {
        this.userId = collections.getCollectionsId().getUserId();
        this.postId = collections.getCollectionsId().getPostId();
    }
}
