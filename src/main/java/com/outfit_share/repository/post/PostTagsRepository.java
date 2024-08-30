package com.outfit_share.repository.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.outfit_share.entity.post.PostTags;
import com.outfit_share.entity.post.PostTagsId;

public interface PostTagsRepository extends JpaRepository<PostTags, PostTagsId> {

    @Query("FROM PostTags WHERE post.postId = ?1")
    List<PostTags> findByPostPostId(Integer postId);

    @Query("FROM PostTags WHERE tags.id = ?1")
    List<PostTags> findByTagsId(Integer Id);
}
