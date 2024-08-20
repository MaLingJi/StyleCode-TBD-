package com.outfit_share.repository.posts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.posts.Collections;
import com.outfit_share.entity.posts.CollectionsId;

public interface CollectionsRepository extends JpaRepository<Collections, CollectionsId> {

}
