package com.outfit_share.repository.posts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.posts.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
