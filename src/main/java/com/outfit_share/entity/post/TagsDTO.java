package com.outfit_share.entity.post;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TagsDTO {

    private Integer id;
    private String name;
    private List<PostTagsDTO> postTags;

    public TagsDTO(Tags tags) {
        this.id = tags.getId();
        this.name = tags.getName();
        for (PostTags postTag : tags.getPostTags()) {
            this.postTags.add(new PostTagsDTO(postTag));
        }
    }
}
