package com.outfit_share.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.post.PostTags;
import com.outfit_share.entity.post.PostTagsId;

public interface PostTagsRepository extends JpaRepository<PostTags, PostTagsId>{

}
