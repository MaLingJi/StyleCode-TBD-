package com.outfit_share.entity.post;

import com.outfit_share.entity.users.UserDetail;

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

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "likes")
public class Likes {

    @EmbeddedId
    private LikesId likesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name="user_id")
    private UserDetail userDetail;
    
    public Likes(Post post, UserDetail userDetail) {
        this.likesId = new LikesId(post.getPostId(), userDetail.getId());
        this.post = post;
        this.userDetail = userDetail;
    }
}
