package com.outfit_share.entity.post;

import org.springframework.stereotype.Component;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "post_tags")
@Component
public class PostTags {

    @EmbeddedId
    private PostTagsId postTagsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagsId")
    @JoinColumn(name = "tag_id")
    private Tags tags;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private Post post;
}
