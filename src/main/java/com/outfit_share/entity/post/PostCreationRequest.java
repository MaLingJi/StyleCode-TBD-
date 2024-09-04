package com.outfit_share.entity.post;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostCreationRequest {

    private PostDTO postDTO;
    private List<String> tagNames;
    private List<ProductTagDTO> productTags;
}
