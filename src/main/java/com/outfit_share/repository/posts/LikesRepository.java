package com.outfit_share.repository.posts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.posts.Likes;
import com.outfit_share.entity.posts.LikesId;

public interface LikesRepository extends JpaRepository<Likes, LikesId> {

}
