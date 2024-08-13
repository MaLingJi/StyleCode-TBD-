package com.outfit_share.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.post.Posts;

public interface PostsRepository extends JpaRepository<Posts, Integer>{

}
