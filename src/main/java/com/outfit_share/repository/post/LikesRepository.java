package com.outfit_share.repository.post;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.post.Likes;
import com.outfit_share.entity.post.LikesId;

public interface LikesRepository extends JpaRepository<Likes, LikesId> {

	 Optional<Likes> findByLikesId_UserIdAndLikesId_PostId(Integer userId, Integer postId);
}
