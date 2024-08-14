package com.outfit_share.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.post.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
