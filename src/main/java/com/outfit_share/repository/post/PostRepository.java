package com.outfit_share.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.post.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{

}
