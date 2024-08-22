package com.outfit_share.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.post.Collections;
import com.outfit_share.entity.post.CollectionsId;

public interface CollectionsRepository extends JpaRepository<Collections, CollectionsId> {

}
