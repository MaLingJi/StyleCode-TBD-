package com.outfit_share.service.post;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.outfit_share.entity.post.Post;
import com.outfit_share.repository.post.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepo;
	
	public Post createPost(Post post) {
		post.setCreatedAt(new Date());
		return postRepo.save(post);
	}
	
	public Post findPostById(Integer postId) {
		Optional<Post> optional = postRepo.findById(postId);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	//軟刪除 設置當前時間
	public Post deletePostById(Integer postId) {
		Optional<Post> deoptional = postRepo.findById(postId);
		if(deoptional.isPresent()) {
			Post post = deoptional.get();
			post.setDeletedAt(new Date());
			return postRepo.save(post);
		}
		return null;
	}
	
	public List<Post> findAllPost(){
		return postRepo.findAll();
	}
	
	@Transactional
	public Post updatePost(Integer postId,Post newpost) {
		Optional<Post> upoptional = postRepo.findById(postId);
		
		if(upoptional.isPresent()) {
			Post post = upoptional.get();
			post.setContentText(newpost.getContentText());//內容
//			post.setPostTitle(newpost.getPostTitle());//標題是否可變更?
//			post.setContentType(newpost.getContentType());//文章型態(分享.討論)可切換變更?
			return postRepo.save(post);
		}
		return null;
	}
	
}
