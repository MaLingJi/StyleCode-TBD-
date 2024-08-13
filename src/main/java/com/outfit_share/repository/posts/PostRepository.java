package com.outfit_share.repository.posts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.Post;
import com.outfit_share.entity.posts.Posts;

public interface PostRepository extends JpaRepository<Posts, Integer> {

}
