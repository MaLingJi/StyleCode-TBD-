package com.outfit_share.repository.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.outfit_share.entity.post.Post;
import org.springframework.data.domain.Pageable;

public interface PostRepository extends JpaRepository<Post, Integer> {

	// 按類型和關鍵字尋找帖子
	@Query("FROM Post p where p.contentType = :contentType AND p.postTitle LIKE %:keyword%")
	List<Post> findPostByTypeAndKeyword(@Param("contentType") String contentType, @Param("keyword") String keyword);

	List<Post> findByUserDetail_Id(Integer userId);
	
	//只取分享區的 資料
	@Query("FROM Post p WHERE p.contentType = :contentType AND p.deletedAt IS NULL")
	List<Post> findLatestPostsByContentType(@Param("contentType") String contentType, Pageable pageable);
	
	//只取前9筆資料
	@Query("FROM Post p WHERE p.deletedAt IS NULL ORDER BY SIZE(p.likes) DESC")
	List<Post> findMostLikedPosts(Pageable pageable);
}
