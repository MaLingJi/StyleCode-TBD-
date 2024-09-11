package com.outfit_share.repository.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.outfit_share.entity.post.Post;
import com.outfit_share.entity.post.PostTags;
import com.outfit_share.entity.post.PostTagsId;

public interface PostTagsRepository extends JpaRepository<PostTags, PostTagsId> {

    @Query("FROM PostTags WHERE post.postId = ?1")
    List<PostTags> findByPostPostId(Integer postId);

    @Query("FROM PostTags WHERE tags.id = ?1")
    List<PostTags> findByTagsId(Integer Id);

    // 自定義方法來刪除某一篇文章的所有 PostTags
    void deleteByPost(Post post);

    // 查詢某一篇文章的所有 PostTags（如果需要）
    List<PostTags> findByPost(Post post);
}
