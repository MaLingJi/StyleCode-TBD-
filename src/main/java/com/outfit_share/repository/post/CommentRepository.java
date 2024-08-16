package com.outfit_share.repository.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.outfit_share.entity.post.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
	//查找所有postId指定deleted為false的評論 獲取與特定文章關聯且為標記為刪除的所有評論
	//檢舉可能用到之類的
	List<Comment> findByPostIdAndDeletedFalse(Integer postId);
}
