package com.outfit_share.repository.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.post.Post;
import com.outfit_share.entity.post.ProductTag;

public interface ProductTagRepository extends JpaRepository<ProductTag, Integer> {

    // 自定義方法來刪除某一篇文章的所有 ProductTag
    void deleteByPost(Post post);
    
    // 您可能還想加上查詢某一篇文章的所有 ProductTag 的方法
    List<ProductTag> findByPost(Post post);
}
