package com.outfit_share.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{

}
