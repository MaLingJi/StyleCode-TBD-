package com.outfit_share.entity.post;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PostCreationRequest {

    private PostDTO postDTO;
    private List<PostTagsDTO> postTags;
    private List<ProductTagDTO> productTags;
}
