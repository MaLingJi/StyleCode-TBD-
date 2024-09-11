package com.outfit_share.repository.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.outfit_share.entity.post.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

	//按類型和關鍵字尋找帖子
	@Query("FROM Post p where p.contentType = :contentType AND p.postTitle LIKE %:keyword%")
	List<Post>findPostByTypeAndKeyword(@Param("contentType") String contentType,@Param("keyword") String keyword);
	
	List<Post> findByUserDetail_Id(Integer userId);
}
