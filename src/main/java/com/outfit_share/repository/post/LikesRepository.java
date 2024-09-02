package com.outfit_share.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.post.Likes;
import com.outfit_share.entity.post.LikesId;

public interface LikesRepository extends JpaRepository<Likes, LikesId> {

	 boolean existsByPostIdAndUserId(Integer postId, Integer userId);

	 boolean existsByLikesId(LikesId likesId);
}
