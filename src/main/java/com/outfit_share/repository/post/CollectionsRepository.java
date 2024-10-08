package com.outfit_share.repository.post;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.post.Collections;
import com.outfit_share.entity.post.CollectionsId;

public interface CollectionsRepository extends JpaRepository<Collections, CollectionsId> {

	 Optional<Collections> findByCollectionsId_UserIdAndCollectionsId_PostId(Integer userId, Integer postId);

	 List<Collections> findByUserDetail_Id(Integer userId);
	 
	}