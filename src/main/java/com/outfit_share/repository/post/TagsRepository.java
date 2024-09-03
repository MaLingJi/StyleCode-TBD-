package com.outfit_share.repository.post;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.post.Tags;

public interface TagsRepository extends JpaRepository<Tags, Integer>{
    Optional<Tags> findByName(String name);

    boolean existsByName(String name);
}
